package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class UserNotFoundException extends RuntimeException implements SubException {
    public UserNotFoundException(long userId){
        super("User with id " + userId + " not found");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

}
