package ua.everybuy.routing.controler.chat.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.chat.ArchiveChatService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.routing.controler.chat.ArchiveChatController;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ArchiveChatControllerImpl implements ArchiveChatController {
    private final ArchiveChatService archiveChatService;

    @Override
    @PostMapping("/add-to-archive")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse addChatToArchive(@RequestParam(name = "chatId") Long chatId, Principal principal){
        return archiveChatService.addChatToArchive(chatId, principal);
    }

    @Override
    @DeleteMapping("/remove-from-archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChatFromArchive(@RequestParam(name = "chatId") Long chatId, Principal principal){
        archiveChatService.deleteChatFromArchive(chatId, PrincipalConvertor.extractPrincipalId(principal));

    }

    @GetMapping("/get-all-archive-chats")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponseForList> getAllUsersArchiveChats(Principal principal){
        return archiveChatService.getAllUsersArchiveChats(principal);
    }
}
