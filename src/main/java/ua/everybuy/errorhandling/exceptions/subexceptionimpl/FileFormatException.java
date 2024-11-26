package ua.everybuy.errorhandling.exceptions.subexceptionimpl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exceptions.SubException;

public class FileFormatException extends SubException {
    public FileFormatException() {
        super("File should be image or pdf only");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
    }
}
