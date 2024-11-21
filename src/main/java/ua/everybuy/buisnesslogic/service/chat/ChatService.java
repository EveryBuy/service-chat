package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.BlackListService;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.repository.ChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.*;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponse;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final BlackListService blackListService;
    private final UserInfoService userInfoService;
    private final AdvertisementInfoService advertisementInfoService;
    private final ChatValidateService chatValidateService;

    public StatusResponse createChat(Long advertisementId, Principal principal) {
        ShortAdvertisementInfoDto advertisementInfo =
                advertisementInfoService.getShortAdvertisementInfo(advertisementId);
        long initiatorId = PrincipalConvertor.extractPrincipalId(principal);
        long adOwnerId = advertisementInfo.getUserId();
        Chat.Section section = Chat.Section.valueOf(advertisementInfo.getSection());

        chatValidateService.validateChatCreation(advertisementId, initiatorId, adOwnerId);
        userInfoService.ensureUserExists(adOwnerId);//if user not present UserNotFoundException will be thrown

        Chat savedChat = chatRepository.save(chatMapper.buildChat(advertisementId, initiatorId, adOwnerId, section));
        return new StatusResponse(HttpStatus.CREATED.value(), chatMapper.mapChatToCreateChatResponse(savedChat));
    }

    public Chat findChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    public StatusResponse getChat(Long chatId, Principal principal) {
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
        } catch (AdvertisementException ex) {
            shortAdvertisementInfo = ShortAdvertisementInfoDto.builder().title(ex.getMessage()).build();
        }
        String section = getChatSection(chat, checkingUserId);
        return new StatusResponse(HttpStatus.OK.value(), chatMapper
                .mapChatToChatResponse(isAnotherUserBlocked, isCurrentlyUserBlocked, chat, userData, shortAdvertisementInfo, section));
    }

    public List<Chat> getAllUserChatsByDateDesc(long userId) {
        return chatRepository.findAllByUserIdOrderByUpdateDateDesc(userId);
    }

    public String getChatSection(Chat chat, long userId) {
        if (chat.getAdOwnerId() == userId) {
            return chat.getSection().name();
        }
        Chat.Section adSection = chat.getSection();
        if (adSection == null) {
            return "UNKNOWN ALD AD";
        }
        return adSection.equals("BUY") ? "SELL" : "BUY";
    }

    public Chat getChatByIdAndUserId(long chatId, Principal principal) {
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        return chatRepository.findChatByIdAndUserId(chatId, userId)
                .orElseThrow(() -> new ChatNotFoundException(chatId, userId));
    }

    public void updateChat(Chat chat) {
        chat.setUpdateDate(LocalDateTime.now());
        chatRepository.save(chat);
    }

    public long getSecondChatMember(long checkingUserId, Chat chat) {
        return checkingUserId == chat.getAdOwnerId() ? chat.getInitiatorId() : chat.getAdOwnerId();
    }


    public List<Chat> getAllChats(){
        return chatRepository.findAll();
    }

    public List<ChatResponse> updateAllChats(){
        getAllChats().stream().filter(chat -> chat.getSection() == null)
                .forEach(chat -> {
                    chat.setSection(Chat.Section.valueOf(advertisementInfoService.getShortAdvertisementInfo(chat.getAdvertisementId()).getSection()));
                    chatRepository.save(chat);
                });

        return getAllChats().stream()
                .map(chatMapper::mapToShortResp)
                .toList();
    }

}
