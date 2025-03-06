package ua.everybuy.buisnesslogic.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.blacklist.BlackListValidateService;
import ua.everybuy.buisnesslogic.service.chat.ArchiveChatService;
import ua.everybuy.buisnesslogic.service.chat.ChatService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.MessageRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.UserNotInChatException;
import ua.everybuy.routing.dto.mapper.MessageMapper;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final BlackListValidateService blackListValidateService;
    private final MessageMapper messageMapper;
    private final ArchiveChatService archiveChatService;

    public MessageResponse createMessage(long chatId, MessageRequest messageRequest, Principal principal){
        validateMessageSendingPermission(chatId);
        Chat chat = chatService.findChatById(chatId);
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        checkUserMembershipInChat(userId, chat);
        Message message = messageMapper.convertRequestToMessage(messageRequest, userId, chat);
        chatService.updateChat(chat);
        messageRepository.save(message);
        archiveChatService.deleteChatFromArchiveIfMessageReceived(chatService.getSecondChatMember(userId, chat), chat);
        return messageMapper.convertMessageToResponse(message);
    }

    private void validateMessageSendingPermission(long chatId){
        Chat chat = chatService.findChatById(chatId);
        long sellerId = chat.getAdOwnerId();
        long buyerId = chat.getInitiatorId();
        blackListValidateService.checkBlock(sellerId, buyerId);
    }

    private void checkUserMembershipInChat(long userId, Chat chat){
        if (userId != chat.getAdOwnerId() && userId != chat.getInitiatorId()){
            throw new UserNotInChatException(userId, chat.getId());
        }
    }

}
