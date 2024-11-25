package ua.everybuy.routing.controler.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.blacklist.BlackListService;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/black-list")
public class BlackListControllerImpl implements BlackListController{
    private final BlackListService blackListService;

    @Override
    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StatusResponse block(Principal principal, @RequestParam(name = "blockedUserId") long blockedUserId){
        return blackListService.blockUser(principal, blockedUserId);
    }

    @Override
    @DeleteMapping("/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblock(Principal principal, @RequestParam(name = "blockedUserId") long blockedUserId){
         blackListService.unblockUser(principal, blockedUserId);
    }
}
