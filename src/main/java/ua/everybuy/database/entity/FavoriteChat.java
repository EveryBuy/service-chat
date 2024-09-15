package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "favorite_chat")
@Getter
@Setter
@NoArgsConstructor
public class FavoriteChat implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne()
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public FavoriteChat(long userId, Chat chat){
        this.chat = chat;
        this.userId = userId;
    }

}
