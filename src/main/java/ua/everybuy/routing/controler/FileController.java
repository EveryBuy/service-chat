package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.FileUrlService;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class FileController {
    private final FileUrlService fileUrlService;

    @PostMapping("/file-upload/{chatId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponse sendFile(@PathVariable long chatId, MultipartFile file, Principal principal) throws IOException {
        return fileUrlService.saveFile(chatId, file, principal);
    }


}
