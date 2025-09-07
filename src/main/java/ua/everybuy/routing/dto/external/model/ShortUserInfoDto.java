package ua.everybuy.routing.dto.external.model;


import java.io.Serializable;

public record ShortUserInfoDto(long userId,
                               String fullName,
                               String photoUrl,
                               boolean isOnline,
                               String lastActivity) implements Serializable {
}
