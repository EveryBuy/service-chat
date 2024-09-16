package ua.everybuy.routing.controler.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.everybuy.routing.dto.external.UserStatusResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/user")
public class ChangeUserInfoController {

    @PostMapping("/change")
    @CachePut(key = "#userInfo.getData().userId()", value = "userInfo")
    public UserStatusResponse changeUserInfo(@RequestBody UserStatusResponse userInfo){
        System.out.println(userInfo);
        System.out.println("changeUserInfo METHOD EXECUTED");
        return userInfo;
    }
}
