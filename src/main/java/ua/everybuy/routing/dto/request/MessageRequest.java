package ua.everybuy.routing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageRequest(@NotNull(message = "Message should not be null.")
                             @NotBlank(message = "Message should not be empty.")
                             @Size(max = 1000, message = "Message can't contains more 1000 symbols.")
                             String text) {
}
