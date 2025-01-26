package ua.everybuy.routing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;


@Getter
@Setter
@AllArgsConstructor
public class StatusResponse {
    private int status;
    private SubResponseMarker data;
}