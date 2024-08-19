package ua.everybuy.routing.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;

@Getter
@Setter
@AllArgsConstructor
public class UserStatusResponse {
    private int status;
    private ShortUserInfoDto data;

    @Override
    public String toString() {
        return "UserStatusResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}