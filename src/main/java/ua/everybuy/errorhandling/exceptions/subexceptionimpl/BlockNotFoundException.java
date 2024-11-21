package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class BlockNotFoundException extends SubException {
    public BlockNotFoundException(long userId, long blockedUserId){
        super("The user with ID " + userId + " has not blocked the user with ID " + blockedUserId);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
