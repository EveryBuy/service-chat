package ua.everybuy.routing.controler.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.errorhandling.ErrorResponse;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

public interface FavoriteChatController {
    @Operation(summary = "Add chat to favorite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chat has been added to favorite",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "ChatId cannot be null",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Chat not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Chat already exists in favorite",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))

    })
    StatusResponse addChatToFavorite(@RequestParam(name = "chatId") Long chatId, Principal principal);

    @Operation(summary = "Remove chat from favorite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Chat has been removed from favorite",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "ChatId cannot be null",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Favorite chat or chat not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),

    })
    void removeChatFromFavorite(@RequestParam(name = "chatId") Long chatId, Principal principal);
}
