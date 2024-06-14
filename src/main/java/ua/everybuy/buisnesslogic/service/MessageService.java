package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.MessageRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockUserException;
import ua.everybuy.routing.dto.mapper.MessageMapper;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final BlackListService blackListService;
    private final MessageMapper messageMapper;

    public StatusResponse createMessage(MessageRequest messageRequest, Principal principal){
        canUserSendMessage(messageRequest, principal);
        Message message = messageMapper.convertRequestToMessage(messageRequest, Long.parseLong(principal.getName()));
        messageRepository.save(message);
        return new StatusResponse(HttpStatus.CREATED.value(), messageMapper.convertMessageToResponse(message));
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    private void canUserSendMessage(MessageRequest messageRequest, Principal principal){
        Chat chat = chatService.findChatById(messageRequest.chatId());
        long sellerId = chat.getSellerId();
        long buyerId = chat.getBuyerId();
        if (blackListService.isUserBlocked(sellerId, buyerId) || blackListService.isUserBlocked(buyerId, sellerId)){
            throw new BlockUserException(Long.parseLong(principal.getName()));
        }
    }

}
