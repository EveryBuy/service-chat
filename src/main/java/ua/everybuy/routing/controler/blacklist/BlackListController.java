package ua.everybuy.routing.controler.blacklist;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.errorhandling.ErrorResponse;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

public interface BlackListController {
    @Operation(summary = "Block user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been blocked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "blockedUserId cannot be null",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already in blacklist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))

    })
    StatusResponse block(Principal principal, @RequestParam(name = "blockedUserId") long blockedUserId);

    @Operation(summary = "Unblock user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User has been unblocked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "blockedUserId cannot be null",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found or user doesn't block",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),

    })
    void unblock(Principal principal, @RequestParam(name = "blockedUserId") long blockedUserId);
}
