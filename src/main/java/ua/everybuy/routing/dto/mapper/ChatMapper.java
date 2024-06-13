package ua.everybuy.routing.dto.mapper;

import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.routing.dto.request.ChatRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.CreateChatResponse;

@Component
public class ChatMapper {
    public Chat mapRequestToChat(ChatRequest chatRequest, long buyerId){
        return Chat.builder()
                .buyerId(buyerId)
                .sellerId(chatRequest.sellerId())
                .advertisementId(chatRequest.advertisementId())
                .build();
    }

    public CreateChatResponse mapChatToResponse(Chat chat){
        return CreateChatResponse.builder()
                .id(chat.getId())
                .advertisementId(chat.getAdvertisementId())
                .creationDate(chat.getCreationDate())
                .buyerId(chat.getBuyerId())
                .sellerId(chat.getSellerId())
                .build();
    }
}
