package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ChatNotFoundException extends IllegalArgumentException implements SubException {
    public ChatNotFoundException (long id){
        super("Chat with id " + id + " not found.");
    }

    public ChatNotFoundException (long chatId, long userId){
        super("Chat with id: " + chatId + " and userId: " + userId + " not found.");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
