package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FileUrl;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

@Component
@RequiredArgsConstructor
public class FileUrlMapper {
    private final UserInfoService userInfoService;

    public FileUrl convertToFileUrl(Chat chat, String fileUrl, long userId){
        return FileUrl.builder()
                .chat(chat)
                .fileUrl(fileUrl)
                .userId(userId)
                .build();
    }

    public FileResponse convertToFileResponse(FileUrl fileUrl){
        return FileResponse
                .builder()
                .id(fileUrl.getId())
                .fileUrl(fileUrl.getFileUrl())
                .chatId(fileUrl.getChat().getId())
                .userId(fileUrl.getUserId())
                .creationTime(fileUrl.getCreationTime())
                .userPhotoUrl(userInfoService.getShortUserInfo(fileUrl.getUserId()).getData().photoUrl())
                .isActive(fileUrl.isActive())
                .isRead(fileUrl.isRead())
                .build();
    }
}
