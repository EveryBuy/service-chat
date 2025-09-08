package ua.everybuy.routing.dto.external.model;


import java.io.Serializable;
import java.util.Date;

public record ShortUserInfoDto(long userId,
                               String fullName,
                               String photoUrl,
                               boolean isOnline,
                               Date lastActivity) implements Serializable {
}
