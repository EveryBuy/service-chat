package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.BlackList;

import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsBlackListByUserIdAndBlockedUserId(long userId, long blockedUserId);

    Optional<BlackList> findByUserIdAndBlockedUserId(long userId, long blockedUserId);
}
