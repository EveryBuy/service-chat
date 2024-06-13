package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.buisnesslogic.util.DateService;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", length = 1000)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime creationTime;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "file_url", length = 1000)
    private String fileUrl;

    @PrePersist
    public void onCreate(){
        this.creationTime = DateService.getDate(LocalDateTime.now());
    }

}
