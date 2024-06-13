package ua.everybuy.routing.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.BlackListService;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/black-list")
public class BlackListController {
    private final BlackListService blackListService;

    @PostMapping("/block/{blockedUserId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StatusResponse block(Principal principal, @PathVariable(name = "blockedUserId") long blockedUserId){
        return blackListService.blockUser(principal, blockedUserId);
    }

    @DeleteMapping("/unblock/{blockedUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblock(Principal principal, @PathVariable(name = "blockedUserId") long blockedUserId){
         blackListService.unblockUser(principal, blockedUserId);
    }
}
