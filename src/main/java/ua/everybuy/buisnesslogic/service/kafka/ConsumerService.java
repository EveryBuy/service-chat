package ua.everybuy.buisnesslogic.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.everybuy.routing.dto.external.model.ShortAdvertisementInfoDto;

@Service
@RequiredArgsConstructor
public class ConsumerService {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "chat-ad-updates", groupId = "advertisementId")
    @Transactional
    @CachePut(cacheNames = "shortAdvertisementInfo", key = "#result.id")
    public ShortAdvertisementInfoDto consume(String message) throws JsonProcessingException {
        ShortAdvertisementInfoDto dto = objectMapper.readValue(message, ShortAdvertisementInfoDto.class);
        System.out.println(dto.getTitle());
        return dto;
    }

}
