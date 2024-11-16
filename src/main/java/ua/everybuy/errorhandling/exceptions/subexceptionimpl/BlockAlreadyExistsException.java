package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class BlockAlreadyExistsException extends IllegalArgumentException implements SubException {

    public BlockAlreadyExistsException(long userId, long blockUserId){
        super("The user with ID " + userId + " has already blocked the user with ID " + blockUserId);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
