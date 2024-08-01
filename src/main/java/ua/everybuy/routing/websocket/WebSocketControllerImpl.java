package ua.everybuy.routing.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketControllerImpl {

    @CrossOrigin("*")
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting  greeting (HelloMessage message) {
        System.out.println(message);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}