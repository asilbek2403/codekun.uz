package dasturlashasil.uz.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Last12ArticleDTO {
    @NotEmpty(message = "Except article id list required")
    private List<String> exceptArticleIdList;
}