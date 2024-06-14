package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

import java.security.Principal;

public class UserIsBlockedException extends RuntimeException implements SubException {
    public UserIsBlockedException (long userId){
        super("The user with ID " + userId + " is blocked");
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
