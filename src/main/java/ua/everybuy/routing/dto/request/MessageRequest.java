package ua.everybuy.routing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record MessageRequest(@NotNull(message = "Message should not be null.")
                             @NotBlank(message = "Message should not be empty.")
                             String text) {

}
