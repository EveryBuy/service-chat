package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.MessageService;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StatusResponse createMessage(Principal principal, @RequestBody MessageRequest messageRequest){
        return messageService.createMessage(messageRequest, principal);
    }
}
