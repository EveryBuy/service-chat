package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.*;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ChatResponse implements SubResponseMarker, Serializable {
    private long id;
    private String section;
    private long advertisementId;
    private String creationDate;
    private String updateAt;
    private long buyerId;
    private long sellerId;
    private boolean isAnotherUserBlocked;
    private boolean isCurrentlyUserBlocked;
    private List<MessageResponse> chatMessages;
    private ShortUserInfoDto userData;
    private ShortAdvertisementInfoDto shortAdvertisementInfo;
}
