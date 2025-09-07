package ua.everybuy.buisnesslogic.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.UserNotFoundException;
import ua.everybuy.routing.dto.external.UserStatusResponse;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final RequestSenderService requestSenderService;
    private final static String USER_INFO_ENDPOINT = "/short-info?userId=";
    @Value("${user.service.url}")
    private String userInfoUrl;

    @Cacheable(key = "#userId", value = "userInfo")
    public UserStatusResponse getShortUserInfo(long userId) {
        return extractUserInfo(userId);
    }


    private void throwExceptionIfUserNotFound(HttpStatusCode statusCode, long userId){
        if (statusCode.equals(HttpStatus.NOT_FOUND)) {
            throw new UserNotFoundException(userId);
        }
    }

    private UserStatusResponse extractUserInfo(long userId){
        String fullUrl = userInfoUrl + USER_INFO_ENDPOINT + userId;
        UserStatusResponse statusResponse;
        System.out.println("inside getShortUserInfo method cache doesn't work");
        try {
            statusResponse = requestSenderService.doRequest(fullUrl, UserStatusResponse.class).getBody();
        } catch (HttpStatusCodeException e) {
            throwExceptionIfUserNotFound(e.getStatusCode(), userId);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return statusResponse;
    }
}