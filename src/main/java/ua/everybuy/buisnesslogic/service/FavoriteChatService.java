package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.chat.ChatService;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FavoriteChat;
import ua.everybuy.database.repository.FavoriteChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FavoriteChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FavoriteChatNotFoundException;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.AddToFavoriteResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class FavoriteChatService {
    private final FavoriteChatRepository favoriteChatRepository;
    private final ChatService chatService;

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
        return Long.parseLong(principal.getName());
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
}
