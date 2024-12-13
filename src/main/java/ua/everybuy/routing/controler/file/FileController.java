package ua.everybuy.routing.controler.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.errorhandling.ErrorResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface FileController {
    @Operation(summary = "Send file to chat",
            description = "Sends a file to a specific chat room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File successfully sent",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid chatId",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is in blacklist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Chat not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    List<FileResponse> sendFile(@PathVariable long chatId, List<MultipartFile> files, Principal principal) throws IOException;
}
