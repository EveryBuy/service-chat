package ua.everybuy.routing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageRequest(@NotNull(message = "Message should be not null")
                             @NotBlank(message = "Message should be not blank") String text,
                             String fileUrl) {

}
