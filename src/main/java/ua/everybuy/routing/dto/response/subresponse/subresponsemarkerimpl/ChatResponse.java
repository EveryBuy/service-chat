package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ChatResponse implements SubResponseMarker {
    private long id;
    private long advertisementId;
    private LocalDateTime creationDate;
    private long buyerId;
    private long sellerId;
    private boolean isBlock;
    private List<MessageResponse> chatMessages;
    private ShortUserInfoDto userData;
    private ShortAdvertisementInfoDto shortAdvertisementInfo;
}
