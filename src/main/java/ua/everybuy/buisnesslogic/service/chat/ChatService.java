package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.blacklist.BlackListValidateService;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.message.ReadContentService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.repository.ChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.*;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final BlackListValidateService blackListValidateService;
    private final UserInfoService userInfoService;
    private final AdvertisementInfoService advertisementInfoService;
    private final ChatValidateService chatValidateService;
    private final ReadContentService readContentService;

    public StatusResponse createChat(Long advertisementId, Principal principal) {
        ShortAdvertisementInfoDto advertisementInfo =
                advertisementInfoService.getShortAdvertisementInfo(advertisementId);
        long initiatorId = PrincipalConvertor.extractPrincipalId(principal);
        long adOwnerId = advertisementInfo.getUserId();
        Chat.Section section = Chat.Section.valueOf(advertisementInfo.getSection());

        chatValidateService.validateChatCreation(advertisementId, initiatorId, adOwnerId);
        blackListValidateService.ensureUserExist(adOwnerId);//if user not present UserNotFoundException will be thrown

        Chat savedChat = chatRepository.save(chatMapper.buildChat(advertisementId, initiatorId, adOwnerId, section));
        return new StatusResponse(HttpStatus.CREATED.value(), chatMapper.mapChatToCreateChatResponse(savedChat));
    }

    public Chat findChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    public StatusResponse getChat(Long chatId, Principal principal) {
        Chat chat = getChatByIdAndUserId(chatId, principal);
        long checkingUserId = PrincipalConvertor.extractPrincipalId(principal);
        long checkedUserId = getSecondChatMember(checkingUserId, chat);
        boolean isAnotherUserBlocked = blackListValidateService.isUserInBlackList(checkingUserId, checkedUserId);
        boolean isCurrentlyUserBlocked = blackListValidateService.isUserInBlackList(checkedUserId, checkingUserId);
        ShortUserInfoDto userData = userInfoService.getShortUserInfo(checkedUserId).getData();
        ShortAdvertisementInfoDto shortAdvertisementInfo;
        try {
            shortAdvertisementInfo = advertisementInfoService
                    .getShortAdvertisementInfo(chat.getAdvertisementId());
        } catch (AdvertisementException ex) {
            shortAdvertisementInfo = ShortAdvertisementInfoDto.builder().title(ex.getMessage()).build();
        }
        String section = getChatSection(chat, checkingUserId);
        readContentService.markContentAsRead(chatId, checkedUserId);
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
        return adSection.equals(Chat.Section.BUY)
                ? Chat.Section.SELL.name()
                : Chat.Section.BUY.name();
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

}
