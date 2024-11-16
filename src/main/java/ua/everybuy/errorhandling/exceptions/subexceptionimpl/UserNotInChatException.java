package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class UserNotInChatException extends IllegalArgumentException implements SubException {
    public UserNotInChatException (long userId, long chatId){
        super("User " + userId + " is not chat " + chatId + " member");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
