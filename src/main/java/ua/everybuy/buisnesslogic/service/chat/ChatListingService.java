package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;
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

    public List<ChatResponseForList> getAllUsersChats(Principal principal) {
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        return chatService.getAllUserChatsByDateDesc(userId)
                .stream()
                .map(chat -> mapChatForList(chat, userId))
                .collect(Collectors.toList());
    }

    private ChatResponseForList mapChatForList(Chat chat, long userId) {
        ShortUserInfoDto userInfo = userInfoService
                .getShortUserInfo(chatService.getSecondChatMember(userId, chat)).getData();
        ShortAdvertisementInfoDto adInfo = advertisementInfoService
                .getShortAdvertisementInfo(chat.getAdvertisementId());
        String section = chatService.getChatSection(chat, userId);
        Message latestMessage = chat.getMessages()
                .stream().max(Comparator.comparing(Message::getCreationTime))
                .orElse(Message.builder().text("no messages yet").build());
        return chatMapper.mapToChatResponseForList(chat, userInfo, latestMessage, section, adInfo.getIsEnabled());
    }


    public List<ChatResponseForList> getUsersChatsBySection(Principal principal, String section) {
        return getAllUsersChats(principal).stream()
                .filter(chat -> chat.getSection().equalsIgnoreCase(section))
                .toList();
    }

}
