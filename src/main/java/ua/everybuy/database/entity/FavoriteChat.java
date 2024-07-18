package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite_chat")
public class FavoriteChat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public FavoriteChat(long userId, Chat chat){
        this.chat = chat;
        this.userId = userId;
    }

    public FavoriteChat(){

    }
}
