package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "archive_chat")
@NoArgsConstructor
public class ArchiveChat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne()
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public ArchiveChat(long userId, Chat chat){
        this.chat = chat;
        this.userId = userId;
    }

}
