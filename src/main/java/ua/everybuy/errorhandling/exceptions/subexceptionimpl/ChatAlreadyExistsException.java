package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ChatAlreadyExistsException extends SubException {

    public ChatAlreadyExistsException(long chatId){
        super("Chat already exists. id:" + chatId);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
