package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.buisnesslogic.service.ChatService;
import ua.everybuy.database.entity.Message;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final ChatService chatService;
    public MessageResponse convertMessageToResponse(Message message){
        return MessageResponse.builder()
                .id(message.getId())
                .text(message.getText())
                .creationTime(message.getCreationTime())
                .userId(message.getUserId())
                .chatId(message.getChat().getId())
                .imageUrl(message.getImageUrl())
                .fileUrl(message.getFileUrl())
                .build();
    }

    public Message convertRequestToMessage(MessageRequest messageRequest, long userId){
        return Message.builder()
                .text(messageRequest.text())
                .userId(userId)
                .chat(chatService.findChatById(messageRequest.chatId()))
                .imageUrl(messageRequest.imageUrl())
                .fileUrl(messageRequest.fileUrl())
                .build();
    }
}
