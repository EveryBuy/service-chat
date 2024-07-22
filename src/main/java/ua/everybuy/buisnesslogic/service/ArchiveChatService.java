package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.ArchiveChat;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.repository.ArchiveChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ArchiveChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ArchiveChatNotFoundException;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.AddToArchiveResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ArchiveChatService {
    private final ArchiveChatRepository archiveChatRepository;
    private final ChatService chatService;

    public StatusResponse addChatToArchive(long chatId, Principal principal){
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = extractUserId(principal);
        if (archiveChatRepository.existsByUserIdAndChat(userId, chat)) {
            throw new ArchiveChatAlreadyExistsException(chatId, userId);
        }
        archiveChatRepository.save(new ArchiveChat(userId, chat));
        return new StatusResponse(HttpStatus.CREATED.value(),new AddToArchiveResponse(chatId, userId));

    }

    private long extractUserId(Principal principal){
        return Long.parseLong(principal.getName());
    }

    public void deleteChatFromArchive(long chatId, Principal principal){
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = extractUserId(principal);
        ArchiveChat archiveChatByUserIdAndChatId = findByChatAndUserId(userId, chat);
        archiveChatRepository.delete(archiveChatByUserIdAndChatId);
    }

    private ArchiveChat findByChatAndUserId(Long userId, Chat chat){
       return archiveChatRepository.findArchiveChatByUserIdAndChat(userId, chat)
               .orElseThrow(() -> new ArchiveChatNotFoundException(chat.getId(), userId));
    }
}
