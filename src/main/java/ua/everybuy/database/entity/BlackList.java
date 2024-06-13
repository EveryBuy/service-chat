package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BlackList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "user_id")
    private long userId;

    @Column (name = "blocked_user_id")
    private long blockedUserId;

    public BlackList(long userId, long blockedUserId){
        this.userId = userId;
        this.blockedUserId = blockedUserId;
    }
}
