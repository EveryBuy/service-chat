package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.ChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.*;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final BlackListService blackListService;
    private final UserInfoService userInfoService;
    private final AdvertisementInfoService advertisementInfoService;

    public StatusResponse createChat(Long advertisementId, Principal principal){
        long buyerId = PrincipalConvertor.extractPrincipalId(principal);
        long sellerId = advertisementInfoService.getShortAdvertisementInfo(advertisementId).getUserId();
        if (blackListService.isUserInBlackList(sellerId, buyerId)) {
            throw new BlockUserException(buyerId);
        }
        checkIfChatPresent(advertisementId, buyerId, sellerId);
        checkIfBuyerIsAdOwner(buyerId, sellerId);
        userInfoService.ensureUserExists(sellerId);//if user not present UserNotFoundException will be thrown
        Chat savedChat = chatRepository.save(chatMapper.buildChat(advertisementId, buyerId, sellerId));
        return new StatusResponse(HttpStatus.CREATED.value(), chatMapper.mapChatToCreateChatResponse(savedChat));
    }

    public Chat findChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    private void checkIfChatPresent(Long advertisementId, Long buyerId, long sellerId){
        boolean isPresent = chatRepository.existsChatByAdvertisementIdAndBuyerIdAndSellerId(
                advertisementId,
                buyerId,
                sellerId);

        if (isPresent) {
            throw new ChatAlreadyExistsException();
        }
    }

    private void checkIfBuyerIsAdOwner(long userId, long buyerId){
        if (userId == buyerId){
            throw new SelfChatCreationException(userId);
        }
    }

    public StatusResponse getChat(Long chatId, Principal principal){
        Chat chat = getChatByIdAndUserId(chatId, principal);
        long advertisementId = chat.getAdvertisementId();
        long checkingUserId = PrincipalConvertor.extractPrincipalId(principal);
        long checkedUserId = getSecondChatMember(checkingUserId, chat);
        boolean isAnotherUserBlocked = blackListService.isUserInBlackList(checkingUserId, checkedUserId);
        boolean isCurrentlyUserBlocked = blackListService.isUserInBlackList(checkedUserId, checkingUserId);
        ShortUserInfoDto userData = userInfoService.getShortUserInfo(checkedUserId).getData();
        ShortAdvertisementInfoDto shortAdvertisementInfo;
        try {
            shortAdvertisementInfo = advertisementInfoService
                    .getShortAdvertisementInfo(advertisementId);
        }catch (AdvertisementException ex){
            shortAdvertisementInfo = ShortAdvertisementInfoDto.builder().title(ex.getMessage()).build();
        }
        return new StatusResponse(HttpStatus.OK.value(), chatMapper
                .mapChatToChatResponse(isAnotherUserBlocked, isCurrentlyUserBlocked, chat,  userData, shortAdvertisementInfo));
    }

    public Chat getChatByIdAndUserId(long chatId, Principal principal){
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        return chatRepository.findChatByIdAndUserId(chatId, userId)
                .orElseThrow(() -> new ChatNotFoundException(chatId, userId));
    }

    public void updateChat(Chat chat){
        chat.setUpdateDate(LocalDateTime.now());
        chatRepository.save(chat);
    }

    public List<ChatResponseForList> getAllUsersChats(Principal principal){
        long userId = PrincipalConvertor.extractPrincipalId(principal);

        return chatRepository.findAllByUserIdOrderByUpdateDateDesc(userId)
                .stream()
                .map(chat -> chatMapper.mapToChatResponseForList(chat,
                        userInfoService.getShortUserInfo(getSecondChatMember(userId, chat)).getData(),
                        chat.getMessages().stream().max(Comparator.comparing(Message::getCreationTime))
                                .orElse(Message.builder().text("no messages yet").build())))

                .collect(Collectors.toList());
    }

    public long getSecondChatMember(long checkingUserId, Chat chat){
       return checkingUserId == chat.getSellerId() ? chat.getBuyerId() : chat.getSellerId();
    }

}
