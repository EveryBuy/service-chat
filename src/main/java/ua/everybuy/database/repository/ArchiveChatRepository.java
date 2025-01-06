package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.ArchiveChat;
import ua.everybuy.database.entity.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchiveChatRepository extends JpaRepository<ArchiveChat, Long> {
    boolean existsByUserIdAndChat(long userId, Chat chat);

    Optional<ArchiveChat> findArchiveChatByUserIdAndChat(long userId, Chat chatId);

    List<ArchiveChat> findArchiveChatsByUserId(long userId);
}
