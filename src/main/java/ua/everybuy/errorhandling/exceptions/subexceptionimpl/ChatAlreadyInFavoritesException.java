package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ChatAlreadyInFavoritesException extends RuntimeException implements SubException {

    public ChatAlreadyInFavoritesException(long chatId, long userId){
        super("The chat with ID: " + chatId + " is already in the favorites for the user with id: " + userId);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }

}
