package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FileUrl;
import ua.everybuy.database.repository.FileUrlRepository;
import ua.everybuy.routing.dto.mapper.FileUrlMapper;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class FileUrlService {
    private final FileUrlRepository fileUrlRepository;
    private final AwsS3Service awsS3Service;
    private final ChatService chatService;
    private final BlackListService blackListService;
    private final FileUrlMapper fileUrlMapper;

    public FileResponse saveFile(long chatId, MultipartFile file, Principal principal) throws IOException {
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        blackListService.checkBlock(userId, chatService.getSecondChatMember(userId, chat));//need to move logic to BlackListService!!!
        String fileUrl = awsS3Service.uploadFile(file);
        FileUrl fileUrlEntity = fileUrlMapper.convertToFileUrl(chat, fileUrl, userId);
        FileUrl newFileUrl = fileUrlRepository.save(fileUrlEntity);
        return fileUrlMapper.convertToFileResponse(newFileUrl);

    }

}
