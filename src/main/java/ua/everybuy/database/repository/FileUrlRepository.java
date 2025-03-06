package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.FileUrl;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    List<FileUrl> findByCreationTimeBeforeAndIsActiveTrue(LocalDateTime localDateTime);

    List<FileUrl> findFileUrlsByChatIdAndUserIdAndIsRead(long chatId, long userId, boolean isRead);

    @Query("SELECT COUNT(f.id) FROM FileUrl f WHERE f.chat.id = :chatId AND f.userId = :userId AND f.isRead = :isRead")
    long countUnreadMessages(@Param("chatId") long chatId,
                             @Param("userId") long userId,
                             @Param("isRead") boolean isRead);
}
