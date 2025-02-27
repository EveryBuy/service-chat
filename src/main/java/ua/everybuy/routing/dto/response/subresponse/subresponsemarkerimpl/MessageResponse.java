package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements SubResponseMarker, ChatContent {
    private Long id;
    private String text;
    private LocalDateTime creationTime;
    private Long userId;
    private Long chatId;
    private String userPhotoUrl;
    private boolean isRead;

    @Override
    @JsonIgnore
    public String getContent(){
        return text;
    }
}
