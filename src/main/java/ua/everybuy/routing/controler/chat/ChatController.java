package ua.everybuy.routing.controler.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.errorhandling.ErrorResponse;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

public interface ChatController {
    @Operation(summary = "Create chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chat has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Chat already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))

    })
    StatusResponse createChatRoom(@RequestParam Long advertisementId, Principal principal);

    @Operation(summary = "Get chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get chat by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Missing or null chatId",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Chat not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    StatusResponse getChat(@PathVariable long id, Principal principal);

}
