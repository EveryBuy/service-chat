package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final MessageMapper messageMapper;
    private final FileUrlMapper fileUrlMapper;

    public Chat buildChat(long advertisementId, long initiatorId, long adOwnerId, Chat.Section section) {
        return Chat.builder()
                .advertisementId(advertisementId)
                .initiatorId(initiatorId)
                .adOwnerId(adOwnerId)
                .section(section)
                .build();
    }

    public CreateChatResponse mapChatToCreateChatResponse(Chat chat) {
        return CreateChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate())
                .buyerId(chat.getInitiatorId())
                .sellerId(chat.getAdOwnerId())
                .build();
    }

//    @Cacheable(value = "chatResponseCacheRedis", key = "#chat.id")
    public ChatResponse mapChatToChatResponse(boolean  isAnotherUserBlocked,
            boolean isCurrentlyUserBlocked,
            Chat chat,
            ShortUserInfoDto shortUserInfoDto,
            ShortAdvertisementInfoDto shortAdvertisementInfo,
                                              String section) {
        System.out.println("Cache doesn't work mapChatToChatResponse, method executed");

        return ChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate().toString())
                .updateAt(chat.getUpdateDate().toString())
                .initiatorId(chat.getInitiatorId())
                .adOwnerId(chat.getAdOwnerId())
                .isAnotherUserBlocked(isAnotherUserBlocked)
                .isCurrentlyUserBlocked(isCurrentlyUserBlocked)
                .chatMessages(getChatContent(chat))
                .userData(shortUserInfoDto)
                .shortAdvertisementInfo(shortAdvertisementInfo)
                .section(section)
                .build();
    }

    public ChatResponseForList mapToChatResponseForList(Chat chat,
                                                        ShortUserInfoDto userData,
                                                        ChatContentResponse chatContentResponse,
                                                        String section,
                                                        boolean isEnabled,
                                                        boolean isAnotherUserBlocked,
                                                        boolean isCurrentlyUserBlocked) {

        ChatResponseForList response = ChatResponseForList.builder()
                .chatId(chat.getId())
                .userData(userData)
                .lastMessage(chatContentResponse.getContent())
                .lastMessageDate(chat.getUpdateDate())
                .section(section)
                .isAdvertisementActive(isEnabled)
                .isAnotherUserBlocked(isAnotherUserBlocked)
                .isCurrentlyUserBlocked(isCurrentlyUserBlocked)
                .build();
        if (chatContentResponse.getClass().equals(MessageResponse.class)){
            response.setText(true);
        }
        return response;
    }

    public List<ChatContentResponse> getChatContent(Chat chat){
        List<MessageResponse> messageResponses = chat.getMessages().stream()
                .map(messageMapper::convertMessageToResponse)
                .toList();

        List<FileResponse> fileResponses = chat.getFileUrls().stream()
                .map(fileUrlMapper::convertToFileResponse)
                .toList();

       return Stream.concat(
                        messageResponses.stream(),
                        fileResponses.stream()
                ).sorted(Comparator.comparing(ChatContentResponse::getCreationTime))
                .toList();
    }

    public ChatResponse mapToShortResp(Chat chat){
        return ChatResponse.builder()
                .section(chat.getSection().name())
                .adOwnerId(chat.getAdOwnerId())
                .initiatorId(chat.getInitiatorId())
                .build();
    }
}
