package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.everybuy.buisnesslogic.service.ChatService;
import ua.everybuy.routing.dto.request.ChatRequest;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<StatusResponse> createChatRoom(@RequestBody ChatRequest chatRequest, Principal principal){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(chatService.createChatRoom(chatRequest, principal));
    }
}
