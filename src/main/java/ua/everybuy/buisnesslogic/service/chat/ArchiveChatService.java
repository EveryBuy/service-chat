package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.integration.AdvertisementInfoService;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.ArchiveChat;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.ArchiveChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ArchiveChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ArchiveChatNotFoundException;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.AddToArchiveResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveChatService {
    private final ArchiveChatRepository archiveChatRepository;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final UserInfoService userInfoService;
    private final AdvertisementInfoService advertisementInfoService;
    private final ChatListingService chatListingService;

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

    private List<ArchiveChat> findAllByUserId(Principal principal){
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        return archiveChatRepository.findArchiveChatsByUserId(userId);
    }

    public List<ChatResponseForList> getAllUsersArchiveChats(Principal principal){
       long userId = PrincipalConvertor.extractPrincipalId(principal);
        return findAllByUserId(principal).stream()
                .map(chat -> chatListingService.mapChatForList(chat.getChat(), userId))
                .toList();
    }


}
