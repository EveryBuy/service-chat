package ua.everybuy.errorhandling.exceptions;

public abstract class SubException extends IllegalArgumentException{
    public SubException(String message){
        super(message);
    }
    public abstract int getStatusCode();
}
