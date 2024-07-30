package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class UserNotFoundException extends RuntimeException implements SubException {
    private final int statusCode;
    public UserNotFoundException(long userId){
        super("User with id " + userId + " not found");
        statusCode = HttpStatus.NOT_FOUND.value();
    }

    public UserNotFoundException(){
        super("User unauthorized");
        statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
