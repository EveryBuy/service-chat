package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class ToLargeContentException extends SubException {
    public ToLargeContentException() {
        super("Content too large. You can send no more than 6 files. Each file no more than 5 megabytes");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.PAYLOAD_TOO_LARGE.value();
    }
}
