package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.entity.FavoriteChat;

import java.util.List;

@Repository
public interface FavoriteChatRepository extends JpaRepository<FavoriteChat, Long> {
    boolean existsByUserIdAndChat(long userId, Chat chat);

    FavoriteChat findFavoriteChatByUserIdAndChat(long userId, Chat chatId);

    List<FavoriteChat> findFavoriteChatsByUserId(long userId);
}
