package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ArchiveChatAlreadyExistsException extends SubException {

    public ArchiveChatAlreadyExistsException(long chatId, long userId){
        super(String.format("The chat with ID: %d is already in the archives for the user with id: %d", chatId, userId));
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }

}
