package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BlackListResponse implements SubResponseMarker {
    private long userId;
    private long blockedUserId;
}
