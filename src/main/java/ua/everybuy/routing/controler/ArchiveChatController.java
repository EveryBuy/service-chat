package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.ArchiveChatService;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ArchiveChatController {
    private final ArchiveChatService archiveChatService;

    @PostMapping("/add-to-archive")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse addChatToFavorite(@RequestParam(name = "chatId") long chatId, Principal principal){
        return archiveChatService.addChatToArchive(chatId, principal);
    }

    @DeleteMapping("/remove-from-archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChatToFavorite(@RequestParam(name = "chatId") long chatId, Principal principal){
        archiveChatService.deleteChatFromArchive(chatId, principal);

    }
}
