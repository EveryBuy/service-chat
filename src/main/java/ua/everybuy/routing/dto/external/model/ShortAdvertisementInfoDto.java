package ua.everybuy.routing.dto.external.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortAdvertisementInfoDto implements Serializable {
    private Long id;
    private String title;
    private String price;
    private Long userId;
    private String mainPhotoUrl;
}
