package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.FileUrl;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    List<FileUrl> findByCreationTimeBeforeAndIsActiveTrue(LocalDateTime localDateTime);

    List<FileUrl> findFileUrlsByChatIdAndUserIdAndIsRead(long chatId, long userId, boolean isRead);

}
