package ua.everybuy.routing.dto.request;

import lombok.Getter;
import lombok.Setter;


public record MessageRequest(String text,
        Long userId,
        Long chatRoomId) {

}
