package ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatResponseForList {
    private long chatId;
    private ShortUserInfoDto userData;
    private String lastMessage;
    private LocalDateTime lastMessageDate;
    private String section;
}
