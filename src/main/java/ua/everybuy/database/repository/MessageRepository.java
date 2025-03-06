package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByChatIdAndUserIdAndIsRead(long chatId, long userId, boolean isRead);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat.id = :chatId AND m.userId = :userId AND m.isRead = :isRead")
    long countUnreadMessages(@Param("chatId") long chatId,
                            @Param("userId") long userId,
                            @Param("isRead") boolean isRead);
}
