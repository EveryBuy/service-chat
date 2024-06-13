package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Chat;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByAdvertisementIdAndSellerIdAndBuyerId(long advertisementId, long sellerId, long buyerId);
    boolean existsChatByAdvertisementIdAndSellerIdAndBuyerId(long advertisementId, long sellerId, long buyerId);
}
