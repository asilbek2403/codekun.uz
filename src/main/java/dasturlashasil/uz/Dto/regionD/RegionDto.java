package dasturlashasil.uz.Dto.regionD;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegionDto {
    private Integer id;
    private Integer orderNum;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private Boolean visible=true;
    private LocalDate createdDate;  // LocalDate bilan moslashtirildi
    private String description;

    private String name;
}