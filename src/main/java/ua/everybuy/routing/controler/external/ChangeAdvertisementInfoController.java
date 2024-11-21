package ua.everybuy.routing.controler.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/advertisement")
public class ChangeAdvertisementInfoController {
    @PostMapping("/change")
    @ResponseBody()
    @ResponseStatus(HttpStatus.OK)
    @CachePut(key = "#advertisementInfoDto.getId()", value = "shortAdvertisementInfo")
    public ShortAdvertisementInfoDto changeUserInfo(@RequestBody ShortAdvertisementInfoDto advertisementInfoDto){
        System.out.println(advertisementInfoDto);
        System.out.println("changeUserInfo METHOD EXECUTED");
        return advertisementInfoDto;
    }
}
