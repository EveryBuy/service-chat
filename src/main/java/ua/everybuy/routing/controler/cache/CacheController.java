package ua.everybuy.routing.controler.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.everybuy.buisnesslogic.service.util.CacheService;

@RestController
@RequestMapping("/chat/cache")
@RequiredArgsConstructor
public class CacheController {
    private final CacheService cacheService;

    @GetMapping("/clear-all")
    @ResponseStatus(HttpStatus.OK)
    public String clearAllCaches(){
        cacheService.clearAllCaches();
        return "All cache cleaned";
    }
}
