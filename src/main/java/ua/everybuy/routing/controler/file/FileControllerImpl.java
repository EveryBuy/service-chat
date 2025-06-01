package ua.everybuy.routing.controler.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.file.FileUrlService;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class FileControllerImpl implements FileController{
    private final FileUrlService fileUrlService;

    @Override
    @PostMapping("/{chatId}/file-upload")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<FileResponse> sendFile(@PathVariable long chatId, @RequestParam List<MultipartFile> files, Principal principal) {
        return fileUrlService.saveFiles(chatId, files, principal);
    }

}
