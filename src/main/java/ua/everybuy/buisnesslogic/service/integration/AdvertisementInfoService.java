package ua.everybuy.buisnesslogic.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.AdvertisementNotFoundException;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;

@Service
@RequiredArgsConstructor
public class AdvertisementInfoService {
    private final RequestSenderService requestSenderService;
    private final static String ADVERTISEMENT_INFO_ENDPOINT = "/info";
    @Value("${ad.service.url}")
    private String advertisementInfoUrl;

    public ShortAdvertisementInfoDto getShortAdvertisementInfo(long advertisementId) {
        String fullUrl = advertisementInfoUrl + "/" +  advertisementId + ADVERTISEMENT_INFO_ENDPOINT;
        ShortAdvertisementInfoDto shortAdvertisementInfoDto;
        try {
            shortAdvertisementInfoDto = requestSenderService.doRequest(fullUrl, ShortAdvertisementInfoDto.class).getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new AdvertisementNotFoundException(advertisementId);
            }
            throw e;
        }
        return shortAdvertisementInfoDto;
    }
}
