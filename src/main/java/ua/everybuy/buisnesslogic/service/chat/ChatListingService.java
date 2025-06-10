package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.blacklist.BlackListValidateService;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.message.ReadContentService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.ArchiveChat;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.repository.ArchiveChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.AdvertisementException;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatContent;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatListingService {
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final UserInfoService userInfoService;
    private final AdvertisementInfoService advertisementInfoService;
    private final ArchiveChatRepository archiveChatRepository;
    private final ReadContentService readContentService;
    private final BlackListValidateService blackListValidateService;

    public List<ChatResponseForList> getUsersChatsBySection(Principal principal, String section) {
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        List<Chat> archiveChats = getUsersArchiveChats(userId);
        return chatService.getAllUserChatsByDateDesc(userId)
                .stream()
                .filter(chat -> section.equals(chatService.getChatSection(chat, userId)))
                .filter(chat -> !archiveChats.contains(chat))
                .map(chat -> mapChatForList(chat, userId))
                .collect(Collectors.toList());
    }

    public ChatResponseForList mapChatForList(Chat chat, long userId) {
        long secondChatMemberId = chatService.getSecondChatMember(userId, chat);
        boolean isAnotherUserBlocked = blackListValidateService.isUserInBlackList(userId, secondChatMemberId);
        boolean isCurrentlyUserBlocked = blackListValidateService.isUserInBlackList(secondChatMemberId, userId);
        ShortUserInfoDto userInfo = userInfoService
                .getShortUserInfo(secondChatMemberId).getData();
        ShortAdvertisementInfoDto adInfo = getShortAdvertisementInfo(chat.getAdvertisementId());
        String section = chatService.getChatSection(chat, userId);
        ChatContent latestContent = getLastChatMessage(chat);
        long unreadContentCount = readContentService.getUnreadContentCount(chat.getId(), secondChatMemberId);
        boolean isRead = readContentService.isLastMessageRead(latestContent, userId);
        return chatMapper.mapToChatResponseForList(chat, userInfo, latestContent, section, adInfo.getIsEnabled(),
                unreadContentCount, isRead, isAnotherUserBlocked, isCurrentlyUserBlocked);
    }

    private ChatContent getLastChatMessage(Chat chat){
        return chatMapper.getChatContent(chat)
                .stream()
                .max(Comparator.comparing(ChatContent::getCreationTime))
                .orElse(new MessageResponse());

    }

    private List<Chat> getUsersArchiveChats(long userId){
        return archiveChatRepository.findArchiveChatsByUserId(userId)
                .stream()
                .map(ArchiveChat::getChat)
                .toList();
    }

    private ShortAdvertisementInfoDto getShortAdvertisementInfo(long advertisementId){
        ShortAdvertisementInfoDto adInfo;
        try{
            adInfo = advertisementInfoService.getShortAdvertisementInfo(advertisementId);
        } catch (AdvertisementException adEx){
            adInfo = ShortAdvertisementInfoDto
                    .builder()
                    .price("unknown")
                    .section("unknown")
                    .id(advertisementId)
                    .title("unknown")
                    .mainPhotoUrl("unknown")
                    .isEnabled(false)
                    .build();
        }
        return adInfo;
    }

}
