package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FileResponse implements SubResponseMarker, ChatContent {
    private Long id;
    private String fileUrl;
    private LocalDateTime creationTime;
    private Long userId;
    private Long chatId;
    private String userPhotoUrl;
}
