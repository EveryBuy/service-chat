package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.routing.dto.request.ChatRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.CreateChatResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final MessageMapper messageMapper;
    public Chat mapRequestToChat(ChatRequest chatRequest, long buyerId){
        return Chat.builder()
                .buyerId(buyerId)
                .sellerId(chatRequest.sellerId())
                .advertisementId(chatRequest.advertisementId())
                .build();
    }

    public CreateChatResponse mapChatToCreateChatResponse(Chat chat){
        return CreateChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate())
                .buyerId(chat.getBuyerId())
                .sellerId(chat.getSellerId())
                .build();
    }

    public ChatResponse mapChatToChatResponse(Chat chat, boolean isBlock){
        List<MessageResponse> messageResponses = chat.getMessages().stream()
                .map(messageMapper::convertMessageToResponse)
                .toList();

        return ChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate())
                .buyerId(chat.getBuyerId())
                .sellerId(chat.getSellerId())
                .isBlock(isBlock)
                .chatMessages(messageResponses)
                .build();
    }
}
