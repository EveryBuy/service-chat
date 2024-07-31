package ua.everybuy.routing.dto.external.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShortAdvertisementInfoDto {
    private Long id;
    private String title;
    private String price;
    private Long userId;
    private String mainPhotoUrl;
}
