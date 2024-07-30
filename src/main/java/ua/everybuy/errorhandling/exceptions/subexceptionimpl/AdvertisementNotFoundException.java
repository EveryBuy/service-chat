package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class AdvertisementNotFoundException extends RuntimeException implements SubException {
    public AdvertisementNotFoundException(long advertisementId) {
        super("Advertisement with id " + advertisementId + " not found");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
