package ua.everybuy.errorhandling.exceptions;

public interface SubException {
    int getStatusCode();
    String getMessage();
}
