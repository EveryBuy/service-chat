package ua.everybuy.routing.dto.external.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortAdvertisementInfoDto{
    private Long id;
    private String section;
    private String title;
    private String price;
    private Long userId;
    private String mainPhotoUrl;
}
