package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import ua.everybuy.errorhandling.exceptions.SubException;

public class AdvertisementException extends IllegalArgumentException implements SubException {
    private final int statusCode;
    public AdvertisementException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
