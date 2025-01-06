package ua.everybuy.routing.controler.chat.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.chat.FavoriteChatService;
import ua.everybuy.routing.controler.chat.FavoriteChatController;
import ua.everybuy.routing.dto.response.StatusResponse;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatResponseForList;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class FavoriteChatControllerImpl  implements FavoriteChatController {
    private final FavoriteChatService favoriteChatService;

    @PostMapping("/add-to-favorite")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse addChatToFavorite(@RequestParam(name = "chatId") Long chatId, Principal principal){
        return favoriteChatService.addChatToFavorite(chatId, principal);
    }

    @DeleteMapping("/remove-from-favorite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChatFromFavorite(@RequestParam(name = "chatId") Long chatId, Principal principal){
        favoriteChatService.deleteChatFromFavorite(chatId, principal);

    }

    @GetMapping("/get-all-favorite-chats")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponseForList> getAllUsersArchiveChats(Principal principal){
        return favoriteChatService.getAllUsersFavoriteChats(principal);
    }
}
