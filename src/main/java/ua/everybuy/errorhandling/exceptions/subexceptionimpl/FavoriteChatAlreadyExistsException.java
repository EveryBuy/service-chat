package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class FavoriteChatAlreadyExistsException extends IllegalArgumentException implements SubException {

    public FavoriteChatAlreadyExistsException(long chatId, long userId){
        super(String.format("The chat with ID: %d is already in the favorites for the user with id: %d", chatId, userId));
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }

}
