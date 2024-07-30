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
    Optional<Chat> findByAdvertisementIdAndSellerIdAndBuyerId(long advertisementId, long buyerId, long sellerId);
    boolean existsChatByAdvertisementIdAndBuyerIdAndSellerId(long advertisementId, long buyerId, long sellerId);
    @Query("SELECT c FROM Chat c WHERE c.buyerId = :userId OR c.sellerId = :userId ORDER BY c.updateDate DESC")
    List<Chat> findAllByUserIdOrderByUpdateDateDesc(@Param("userId") long userId);

    @Query("SELECT c FROM Chat c WHERE c.id = :id AND (c.buyerId = :userId OR c.sellerId = :userId)")
    Optional<Chat> findChatByIdAndUserId(long id, @Param("userId") long userId);
}


