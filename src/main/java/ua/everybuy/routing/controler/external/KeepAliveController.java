package ua.everybuy.routing.controler.external;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class KeepAliveController {
    @GetMapping("/keep-alive")
    public String keepAlive(){
        return "Chat service waked up";
    }
}
