package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.MessageRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockUserException;
import ua.everybuy.routing.dto.mapper.MessageMapper;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final BlackListService blackListService;
    private final MessageMapper messageMapper;

    public MessageResponse createMessage(long chatId, MessageRequest messageRequest, Principal principal){
        validateMessageSendingPermission(chatId, principal);
        Chat chat = chatService.findChatById(chatId);
        Message message = messageMapper.convertRequestToMessage(messageRequest, Long.parseLong(principal.getName()), chat);
        chatService.updateChat(chat);
        messageRepository.save(message);
        return messageMapper.convertMessageToResponse(message);
    }

    private void validateMessageSendingPermission(long chatId, Principal principal){
        Chat chat = chatService.findChatById(chatId);
        long sellerId = chat.getSellerId();
        long buyerId = chat.getBuyerId();
        if (blackListService.checkBlock(sellerId, buyerId)){
            throw new BlockUserException(Long.parseLong(principal.getName()));
        }
    }


}
