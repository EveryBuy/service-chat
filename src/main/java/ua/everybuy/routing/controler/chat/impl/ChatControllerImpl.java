package ua.everybuy.routing.controler.chat.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.ChatService;
import ua.everybuy.routing.controler.chat.ChatController;
import ua.everybuy.routing.dto.request.ChatRequest;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;

    @Override
    @PostMapping("/create")
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse createChatRoom(@RequestBody @Valid ChatRequest chatRequest, Principal principal){
        return chatService.createChatRoom(chatRequest, principal);
    }

    @Override
    @GetMapping
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    public StatusResponse getChat(@RequestParam long id, Principal principal){
        return chatService.getChat(id, principal);
    }
}
