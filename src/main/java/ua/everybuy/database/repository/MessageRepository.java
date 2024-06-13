package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
