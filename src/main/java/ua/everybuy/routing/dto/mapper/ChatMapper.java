package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
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

    public Chat buildChat(long advertisementId, long buyerId, long sellerId) {
        return Chat.builder()
                .advertisementId(advertisementId)
                .buyerId(buyerId)
                .sellerId(sellerId)
                .build();
    }

    public CreateChatResponse mapChatToCreateChatResponse(Chat chat) {
        return CreateChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate())
                .buyerId(chat.getBuyerId())
                .sellerId(chat.getSellerId())
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
                .userId(chat.getBuyerId())
                .adOwnerId(chat.getSellerId())
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
                                                        Message message,
                                                        String section,
                                                        boolean isEnabled) {
        return ChatResponseForList.builder()
                .chatId(chat.getId())
                .userData(userData)
                .lastMessage(message.getText())
                .lastMessageDate(chat.getUpdateDate())
                .section(section)
                .isAdvertisementActive(isEnabled)
                .build();
    }

    private List<ChatContent> getChatContent(Chat chat){
        List<MessageResponse> messageResponses = chat.getMessages().stream()
                .map(messageMapper::convertMessageToResponse)
                .toList();

        List<FileResponse> fileResponses = chat.getFileUrls().stream()
                .map(fileUrlMapper::convertToFileResponse)
                .toList();

       return Stream.concat(
                        messageResponses.stream(),
                        fileResponses.stream()
                ).sorted(Comparator.comparing(ChatContent::getCreationTime))
                .toList();
    }
}
