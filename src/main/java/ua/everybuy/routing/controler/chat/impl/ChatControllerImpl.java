package ua.everybuy.routing.controler.chat.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.ChatService;
import ua.everybuy.routing.controler.chat.ChatController;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;

    @Override
    @PostMapping("/create")
    @ResponseBody()
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse createChatRoom(@RequestParam Long advertisementId, Principal principal){
        return chatService.createChat(advertisementId, principal);
    }

    @Override
    @GetMapping("/{chatId}")
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    public StatusResponse getChat(@PathVariable long chatId, Principal principal){
        return chatService.getChat(chatId, principal);
    }

    @GetMapping("/get-all-users-chats")
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponseForList> getAllUsersChats(Principal principal){
        return chatService.getAllUsersChats(principal);
    }

    @GetMapping("/get-buy-users-chats")
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponseForList> getBuyUsersChats(Principal principal){
        return chatService.getBuyUsersChats(principal);
    }

    @GetMapping("/get-sell-users-chats")
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponseForList> getSellUsersChats(Principal principal){
        return chatService.getSellUsersChats(principal);
    }

}
