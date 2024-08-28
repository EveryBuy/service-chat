package ua.everybuy.routing.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FileUrl;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class FileUrlMapper {
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
                .build();
    }
}
