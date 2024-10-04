package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.CreateChatResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final MessageMapper messageMapper;

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
            ShortAdvertisementInfoDto shortAdvertisementInfo) {
        System.out.println("Cache doesn't work mapChatToChatResponse, method executed");
        List<MessageResponse> messageResponses = chat.getMessages().stream()
                .map(messageMapper::convertMessageToResponse)
                .toList();

        return ChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate().toString())
                .updateAt(chat.getUpdateDate().toString())
                .buyerId(chat.getBuyerId())
                .sellerId(chat.getSellerId())
                .isAnotherUserBlocked(isAnotherUserBlocked)
                .isCurrentlyUserBlocked(isCurrentlyUserBlocked)
                .chatMessages(messageResponses)
                .userData(shortUserInfoDto)
                .shortAdvertisementInfo(shortAdvertisementInfo)
                .build();
    }

    public ChatResponseForList mapToChatResponseForList(Chat chat, ShortUserInfoDto userData, Message message) {
        return ChatResponseForList.builder()
                .chatId(chat.getId())
                .userData(userData)
                .lastMessage(message.getText())
                .lastMessageDate(chat.getUpdateDate())
                .build();
    }
}
