package ua.everybuy.routing.dto.mapper;

import org.springframework.stereotype.Component;
import ua.everybuy.database.entity.BlackList;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.BlackListResponse;

@Component
public class BlackListMapper {
    public BlackListResponse convertToResponse(BlackList blackList){
        return new BlackListResponse(blackList.getUserId(), blackList.getBlockedUserId());
    }
}
