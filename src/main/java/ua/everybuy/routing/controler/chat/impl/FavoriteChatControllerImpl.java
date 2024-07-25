package ua.everybuy.routing.controler.chat.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.FavoriteChatService;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class FavoriteChatControllerImpl {
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
}
