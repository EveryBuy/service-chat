package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class BlockUserException extends SubException {
    public BlockUserException(long userId){
        super("The user with ID " + userId + " is blocked");
    }

    public BlockUserException(){
        super("The user is blocked");
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
