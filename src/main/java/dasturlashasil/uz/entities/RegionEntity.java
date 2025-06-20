package dasturlashasil.uz.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="region")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer orderNum;

    @Column
    private String description;

    @Column(unique= true, nullable = false)
    private String key;

    private Boolean visible;

    private LocalDate createdDate;  // DTO bilan mos

    private String nameUz;
    private String nameRu;
    private String nameEn;


//    private String name;
}
