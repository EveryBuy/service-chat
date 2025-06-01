package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

public interface ChatContentResponse extends SubResponseMarker {
    LocalDateTime getCreationTime();
    String getContent();
    boolean isRead();
}
