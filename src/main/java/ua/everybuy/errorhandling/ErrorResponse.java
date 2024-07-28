package ua.everybuy.errorhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ErrorResponse {
    private int status;
    private MessageErrorResponse messageErrorResponse;

    public ErrorResponse(int status, String message){
        this.status = status;
        this.messageErrorResponse = new MessageErrorResponse(message);
    }

}
