package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FavoriteChat;
import ua.everybuy.database.repository.FavoriteChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatAlreadyInFavoritesException;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.AddToFavoriteResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
// todo implement deleted from favorite logic
public class FavoriteChatService {
    private final FavoriteChatRepository favoriteChatRepository;
    private final ChatService chatService;

    public StatusResponse addChatToFavorite(long chatId, Principal principal){
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = extractUserId(principal);
        if (favoriteChatRepository.existsByUserIdAndChat(userId, chat)) {
            throw new ChatAlreadyInFavoritesException(chatId, userId);
        }
        favoriteChatRepository.save(new FavoriteChat(userId, chat));
        return new StatusResponse(HttpStatus.CREATED.value(),new AddToFavoriteResponse(chatId, userId));

    }

    private long extractUserId(Principal principal){
        return Long.parseLong(principal.getName());
    }

}
