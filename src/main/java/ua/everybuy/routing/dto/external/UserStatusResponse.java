package ua.everybuy.routing.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.everybuy.routing.dto.external.model.ShortUserInfoDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusResponse implements Serializable {
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