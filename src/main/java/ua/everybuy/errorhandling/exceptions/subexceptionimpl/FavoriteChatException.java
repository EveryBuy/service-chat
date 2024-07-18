package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class FavoriteChatException extends RuntimeException implements SubException {

    public FavoriteChatException(String message, long chatId, long userId){
        super(String.format(message, chatId, userId));
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }

}
