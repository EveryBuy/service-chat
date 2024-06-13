package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateChatResponse implements SubResponseMarker {
    private long id;
    private long advertisementId;
    private LocalDateTime creationDate;
    private long buyerId;
    private long sellerId;
}
