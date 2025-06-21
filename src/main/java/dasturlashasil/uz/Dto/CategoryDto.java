package dasturlashasil.uz.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {

    private Integer id;
    @NotNull(message = "orderNumber required")
    @Min(value = 1, message = "orderNumber have to higher than 0")
    private Integer orderNumber;
    @NotBlank(message = "nameUz required")
    private String nameUz;
    @NotBlank(message = "nameRu required")
    private String nameRu;
    @NotBlank(message = "nameEn required")
    private String nameEn;

    @NotNull(message = "categoryKey required")
    private String categoryKey;
    private LocalDateTime createdDate;
    private String name; // kerakli Lanuage bilan ishlaman

}

