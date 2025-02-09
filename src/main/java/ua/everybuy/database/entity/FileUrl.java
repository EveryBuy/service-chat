package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.everybuy.buisnesslogic.service.util.DateService;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_url")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUrl implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "id")
    private long id;

    @Column(name = "created_at")
    private LocalDateTime creationTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @PrePersist
    public void onCreate(){
        this.creationTime = DateService.getDate(LocalDateTime.now());
        this.isActive = true;
    }

}
