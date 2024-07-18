package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    public MessageResponse convertMessageToResponse(Message message){
        return MessageResponse.builder()
                .id(message.getId())
                .text(message.getText())
                .creationTime(message.getCreationTime())
                .userId(message.getUserId())
                .chatId(message.getChat().getId())
                .fileUrl(message.getFileUrl())
                .build();
    }

    public Message convertRequestToMessage(MessageRequest messageRequest, long userId, Chat chat){
        return Message.builder()
                .text(messageRequest.text())
                .userId(userId)
                .chat(chat)
                .fileUrl(messageRequest.fileUrl())
                .build();
    }
}
