package ua.everybuy.buisnesslogic.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FavoriteChat;
import ua.everybuy.database.repository.FavoriteChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FavoriteChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FavoriteChatNotFoundException;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.AddToFavoriteResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteChatService {
    private final FavoriteChatRepository favoriteChatRepository;
    private final ChatService chatService;
    private final ChatListingService chatListingService;

    public StatusResponse addChatToFavorite(long chatId, Principal principal){
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = extractUserId(principal);
        if (favoriteChatRepository.existsByUserIdAndChat(userId, chat)) {
            throw new FavoriteChatAlreadyExistsException(chatId, userId);
        }
        favoriteChatRepository.save(new FavoriteChat(userId, chat));
        return new StatusResponse(HttpStatus.CREATED.value(),new AddToFavoriteResponse(chatId, userId));

    }

    private long extractUserId(Principal principal){
        return PrincipalConvertor.extractPrincipalId(principal);
    }

    public void deleteChatFromFavorite (long chatId, Principal principal){
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = extractUserId(principal);
        FavoriteChat favoriteChatByUserIdAndChatId = favoriteChatRepository.findFavoriteChatByUserIdAndChat(userId, chat);
        if (favoriteChatByUserIdAndChatId == null) {
            throw new FavoriteChatNotFoundException(chatId, userId);
        }
        favoriteChatRepository.delete(favoriteChatByUserIdAndChatId);
    }

    private List<FavoriteChat> findAllByUserId(Principal principal){
        long userId = extractUserId(principal);
        return favoriteChatRepository.findFavoriteChatsByUserId(userId);
    }

    public List<ChatResponseForList> getAllUsersFavoriteChats(Principal principal){
        long userId = extractUserId(principal);
        return findAllByUserId(principal).stream()
                .map(chat -> chatListingService.mapChatForList(chat.getChat(), userId))
                .toList();
    }
}
