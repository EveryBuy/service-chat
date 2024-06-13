package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ChatNotFoundException extends RuntimeException implements SubException {
    public ChatNotFoundException (long id){
        super("Chat with id " + id + " not found.");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
