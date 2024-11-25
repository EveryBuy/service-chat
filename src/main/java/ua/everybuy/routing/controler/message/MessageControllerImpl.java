package ua.everybuy.routing.controler.message;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.MessageService;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController{
    private final MessageService messageService;


    @CrossOrigin("*")
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse messaging(@DestinationVariable long chatId, @Valid MessageRequest message, Principal principal) {
        MessageResponse response = messageService.createMessage(chatId, message, principal);
        System.out.println(response);
        return response;
    }

    @Override
    @PostMapping("/chat/{chatId}/send-message")
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@PathVariable long chatId, @RequestBody @Valid MessageRequest message, Principal principal) {
        MessageResponse response = messageService.createMessage(chatId, message, principal);
        System.out.println(response);
        return response;
    }

}
