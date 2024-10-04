package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class SelfChatCreationException extends RuntimeException implements SubException {
    public SelfChatCreationException(long userId){
        super("User " + userId + " is advertisement owner");
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}