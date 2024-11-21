package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class FavoriteChatNotFoundException extends SubException {
    public FavoriteChatNotFoundException(long chatId, long userId){
        super(String.format("The chat with ID: %d and user id: %d not found in favorites.", chatId, userId));
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
