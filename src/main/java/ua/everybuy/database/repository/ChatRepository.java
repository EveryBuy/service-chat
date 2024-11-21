package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    boolean existsChatByAdvertisementIdAndInitiatorIdAndAdOwnerId(long advertisementId, long initiatorId, long adOwnerId);
    @Query("SELECT c FROM Chat c WHERE c.initiatorId = :userId OR c.adOwnerId = :userId ORDER BY c.updateDate DESC")
    List<Chat> findAllByUserIdOrderByUpdateDateDesc(@Param("userId") long userId);

    @Query("SELECT c FROM Chat c WHERE c.id = :id AND (c.initiatorId = :userId OR c.adOwnerId = :userId)")
    Optional<Chat> findChatByIdAndUserId(long id, @Param("userId") long userId);
}


