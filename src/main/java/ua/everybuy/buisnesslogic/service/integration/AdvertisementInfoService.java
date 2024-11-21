package ua.everybuy.buisnesslogic.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.AdvertisementException;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;

@Service
@RequiredArgsConstructor
public class AdvertisementInfoService {
    private final RequestSenderService requestSenderService;
    private final static String ADVERTISEMENT_INFO_ENDPOINT = "/info";
    @Value("${ad.service.url}")
    private String advertisementInfoUrl;

    @Cacheable(key = "#advertisementId", value = "shortAdvertisementInfo")
    public ShortAdvertisementInfoDto getShortAdvertisementInfo(long advertisementId) {
        System.out.println("inside getShortAdvertisementInfo method, cache doesn't work");
        return extractAdvertisementInfo(advertisementId);
    }

    private void throwExceptionIfAdvertisementNotFound(HttpStatusCode statusCode, long advertisementId){
        if (statusCode.equals(HttpStatus.NOT_FOUND)){
            throw new AdvertisementException(statusCode.value(),
                    "Advertisement with id " + advertisementId + " not found.");
        }
    }

    private ShortAdvertisementInfoDto extractAdvertisementInfo(long advertisementId){
        String fullUrl = advertisementInfoUrl + "/" +  advertisementId + ADVERTISEMENT_INFO_ENDPOINT;
        ShortAdvertisementInfoDto shortAdvertisementInfoDto;
        try {
            shortAdvertisementInfoDto = requestSenderService.doRequest(fullUrl, ShortAdvertisementInfoDto.class).getBody();
        } catch (HttpStatusCodeException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            throwExceptionIfAdvertisementNotFound(statusCode, advertisementId);
            throw e;
        }
        return shortAdvertisementInfoDto;
    }

}
