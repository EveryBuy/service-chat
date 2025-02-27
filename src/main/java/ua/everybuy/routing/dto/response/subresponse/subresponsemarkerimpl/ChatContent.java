package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

public interface ChatContent extends SubResponseMarker {
    LocalDateTime getCreationTime();
    String getContent();
    boolean isRead();
}
