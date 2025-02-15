package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.blacklist.BlackListValidateService;
import ua.everybuy.buisnesslogic.service.chat.ChatService;
import ua.everybuy.buisnesslogic.service.util.DateService;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FileUrl;
import ua.everybuy.database.repository.FileUrlRepository;
import ua.everybuy.routing.dto.mapper.FileUrlMapper;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.FileResponse;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUrlService {
    private final FileUrlRepository fileUrlRepository;
    private final AwsS3Service awsS3Service;
    private final ChatService chatService;
    private final BlackListValidateService blackListValidateService;
    private final FileUrlMapper fileUrlMapper;

    public FileResponse saveFile(long chatId, MultipartFile file, Principal principal) throws IOException {
        Chat chat = chatService.getChatByIdAndUserId(chatId, principal);
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        blackListValidateService.checkBlock(userId, chatService.getSecondChatMember(userId, chat));//need to move logic to BlackListService!!!
        String fileUrl = awsS3Service.uploadFile(file);
        FileUrl fileUrlEntity = fileUrlMapper.convertToFileUrl(chat, fileUrl, userId);
        FileUrl newFileUrl = fileUrlRepository.save(fileUrlEntity);
        return fileUrlMapper.convertToFileResponse(newFileUrl);

    }

    @Transactional
    public List<FileResponse> saveFiles (long chatId, List<MultipartFile> files, Principal principal){
        return files.stream()
                .map(file -> {
                    try {
                        return saveFile(chatId, file, principal);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }


    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanupOldFiles(){
        LocalDateTime expirationTime = DateService.getDate(LocalDateTime.now().minusDays(10));
        List <FileUrl> oldFiles = fileUrlRepository.findByCreationTimeBeforeAndIsActiveTrue(expirationTime);
                oldFiles.forEach(fileUrl -> {
                    fileUrl.setActive(false);
                    fileUrlRepository.save(fileUrl);
                    awsS3Service.deleteFile(fileUrl.getFileUrl());
                });
        log.info("Deleted {} old files", oldFiles.size());
    }
}
