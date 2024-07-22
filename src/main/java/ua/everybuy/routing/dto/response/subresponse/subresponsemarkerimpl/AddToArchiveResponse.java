package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.Getter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

@Getter
public class AddToArchiveResponse implements SubResponseMarker {
    private final long chatId;
    private final long userId;
    private final String message;

    public AddToArchiveResponse (long chatId, long userId){
        this.chatId = chatId;
        this.userId = userId;
        this.message = "Chat with id " + chatId + " successfully added to archive by user " + userId;
    }
}
