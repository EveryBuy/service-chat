package ua.everybuy.buisnesslogic.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.FileUrl;
import ua.everybuy.database.entity.Message;
import ua.everybuy.database.repository.FileUrlRepository;
import ua.everybuy.database.repository.MessageRepository;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.ChatContent;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadContentService {
    private final MessageRepository messageRepository;
    private final FileUrlRepository fileUrlRepository;

    public void markContentAsRead(long chatId, long userId){
        findAllUnreadMessages(chatId, userId).forEach(this::updateMessage);
        findAllUnreadFileUrls(chatId, userId).forEach(this::updateFileUrl);
    }

    private List<Message> findAllUnreadMessages(long chatId, long userId){
        return messageRepository.findMessagesByChatIdAndUserIdAndIsRead(chatId, userId, false);
    }

    private List<FileUrl> findAllUnreadFileUrls(long chatId, long userId){
        return fileUrlRepository.findFileUrlsByChatIdAndUserIdAndIsRead(chatId, userId, false);
    }

    private void updateMessage(Message message){
        message.setRead(true);
        messageRepository.save(message);
    }

    private void updateFileUrl(FileUrl fileUrl){
        fileUrl.setRead(true);
        fileUrlRepository.save(fileUrl);
    }

    public long getUnreadContentCount(long chatId, long userId){
        return messageRepository.countUnreadMessages(chatId, userId, false)
                + fileUrlRepository.countUnreadMessages(chatId, userId, false);
    }


    public boolean isLastMessageRead(ChatContent chatContent, long userId){
        if (chatContent.getUserId() == null){
            return false;
        }
        if (chatContent.getUserId() != userId){
            return true;
        }
        return chatContent.isRead();
    }

}
