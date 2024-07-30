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
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatNotFoundException;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
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

    public StatusResponse createChatRoom(Long advertisementId, Principal principal){
        ShortAdvertisementInfoDto advertisement = advertisementInfoService.getShortAdvertisementInfo(advertisementId);
        long buyerId = PrincipalConvertor.extractPrincipalId(principal);
        long sellerId = advertisement.getUserId();
        checkIfChatPresent(advertisementId, buyerId, sellerId);
        ensureUserExists(sellerId);//if user not present UserNotFoundException will be thrown
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

    public StatusResponse getChat(Long chatId, Principal principal){
        Chat chat = findChatById(chatId);
        long advertisementId = chat.getAdvertisementId();
        long checkingUserId = PrincipalConvertor.extractPrincipalId(principal);
        long checkedUserId = getSecondChatMember(checkingUserId, chat);
        boolean isBlock = blackListService.isUserInBlackList(checkingUserId, checkedUserId);
        ShortUserInfoDto userData = userInfoService.getShortUserInfo(checkedUserId).getData();
        ShortAdvertisementInfoDto shortAdvertisementInfo = advertisementInfoService
                .getShortAdvertisementInfo(advertisementId);
        return new StatusResponse(HttpStatus.OK.value(), chatMapper
                .mapChatToChatResponse(chat, isBlock, userData, shortAdvertisementInfo));
    }

    public Chat getChatByIdAndUserId(long chatId, Principal principal){
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        return chatRepository.findChatByIdAndUserId(chatId, userId)
                .orElseThrow(() -> new ChatNotFoundException(chatId, userId));
    }

    public void updateChat(Chat chat){
        chatRepository.save(chat);
    }

    public List<ChatResponseForList> getAllUsersChats(Principal principal){
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        Message message = new Message();
        message.setText("text");

        return chatRepository.findAllByUserIdOrderByUpdateDateDesc(userId)
                .stream()
                .map(chat -> chatMapper.mapToChatResponseForList(chat,
                        userInfoService.getShortUserInfo(getSecondChatMember(userId, chat)).getData(),
                        message))
                .collect(Collectors.toList());
    }

    private long getSecondChatMember(long checkingUserId, Chat chat){
       return checkingUserId == chat.getSellerId() ? chat.getBuyerId() : chat.getSellerId();
    }

    private void ensureUserExists(long sellerId){
        userInfoService.getShortUserInfo(sellerId);
    }

}
