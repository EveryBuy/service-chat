package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ArchiveChatNotFoundException extends IllegalArgumentException implements SubException {
    public ArchiveChatNotFoundException(long chatId, long userId){
        super(String.format("The chat with ID: %d and user id: %d is not exists in the archives", chatId, userId));
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
