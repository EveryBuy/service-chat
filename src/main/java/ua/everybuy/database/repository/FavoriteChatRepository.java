package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FavoriteChat;

@Repository
public interface FavoriteChatRepository extends JpaRepository<FavoriteChat, Long> {
    public boolean existsByUserIdAndChat(long userId, Chat chat);
}
