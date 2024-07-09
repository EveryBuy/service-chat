package ua.everybuy.routing.controler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class WebSocketController {

    @CrossOrigin("*")
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public String processMessage(String message) {
        System.out.println(message);
        return "Server says: " + message;
    }
}