package ua.everybuy.routing.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChatRequest(@NotNull(message = "AdvertisementId should be not null.") Long advertisementId,
                          @NotNull(message = "SellerId should be not null.") Long sellerId) {
}
