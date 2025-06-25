package dasturlashasil.uz.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension;

    @Column(name = "origen_name")
    private String origenName;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "visible")
    private Boolean visible = true;
}


