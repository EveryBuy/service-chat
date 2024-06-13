package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.MessageRepository;
import ua.everybuy.routing.dto.request.MessageRequest;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public Message createMessage(MessageRequest messageRequest){
        Message message = new Message();
        message.setText(messageRequest.text());
        message.setUserId(messageRequest.userId());
        message.setChat(chatService.findChatById(messageRequest.chatRoomId()));

        return messageRepository.save(message);
    }
}
