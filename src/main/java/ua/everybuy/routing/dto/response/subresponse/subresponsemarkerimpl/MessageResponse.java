package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageResponse implements SubResponseMarker {
    private Long id;
    private String text;
    private LocalDateTime creationTime;
    private Long userId;
    private Long chatId;
    private String imageUrl;
    private String fileUrl;
}
