package ua.everybuy.routing.dto.request;

import java.util.List;

public record ValidRequest(int status,
        AuthUserInfoDto data) {


    public record   AuthUserInfoDto(
            Boolean isValid,
            long userId,
            String email,
            String phoneNumber,
            List<String> roles){

    }
}
