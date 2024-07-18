package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.FavoriteChatService;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class FavoriteChatController {
    private final FavoriteChatService favoriteChatService;

    @PostMapping("/add-to-favorite")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse addChatToFavorite(@RequestParam(name = "chatId") long chatId, Principal principal){
        return favoriteChatService.addChatToFavorite(chatId, principal);
    }

    @PostMapping("/remove-from-favorite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChatToFavorite(@RequestParam(name = "chatId") long chatId, Principal principal){
        favoriteChatService.deleteChatFromFavorite(chatId, principal);

    }
}
