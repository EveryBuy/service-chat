package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.everybuy.buisnesslogic.service.util.DateService;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad_id")
    private long advertisementId;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    @Column(name = "buyer_id")
    private long buyerId;

    @Column(name = "seller_id")
    private long sellerId;

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<FavoriteChat> favoriteChats;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ArchiveChat> archiveChats;

    @PrePersist
    public void onCreate(){
        creationDate = updateDate = DateService.getDate(LocalDateTime.now());
    }

    public void setUpdateDate(LocalDateTime updateDate){
        this.updateDate = DateService.getDate(updateDate);
    }
}

