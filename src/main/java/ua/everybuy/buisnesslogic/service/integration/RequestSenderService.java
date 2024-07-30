package ua.everybuy.buisnesslogic.service.integration;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class RequestSenderService {
    private final static String AUTH_HEADER_PREFIX = "Authorization";
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> doRequest(HttpServletRequest request, String url, Class<T> tClass){
        String authHeader = request.getHeader(AUTH_HEADER_PREFIX);
        HttpEntity<HttpHeaders> requestEntity = getHttpHeadersHttpEntity(authHeader);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                tClass);
    }

    public <T> ResponseEntity<T> doRequest(String url, Class<T> tClass){
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                tClass);
    }

    private static HttpEntity<HttpHeaders> getHttpHeadersHttpEntity(String authHeader) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(AUTH_HEADER_PREFIX, authHeader);
        return new HttpEntity<>(null, headers);
    }

}

