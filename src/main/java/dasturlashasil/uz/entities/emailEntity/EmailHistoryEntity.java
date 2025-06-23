package dasturlashasil.uz.entities.emailEntity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="email_history")
public class EmailHistoryEntity {

//email ga 5,...8 .. har haftasms ketishi mn
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="to_account")
    private String toAccount ;

    @Column(name="body")
    private String body ;

    @Column(name="code")
    private String code ;

    @Column(name="created_date")
    @CreationTimestamp//time avtomatik bor
    private LocalDateTime createdDate ;




}
