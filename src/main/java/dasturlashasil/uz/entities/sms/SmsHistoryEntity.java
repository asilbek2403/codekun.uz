package dasturlashasil.uz.entities.sms;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "body")
    private String body;

    @Column(name = "code")
    private String code;

    @Column(name = "attempt_count")
    private Integer attemptCount = 0;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
