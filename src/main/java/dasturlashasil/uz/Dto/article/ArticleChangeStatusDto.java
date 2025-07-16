package dasturlashasil.uz.Dto.article;

import dasturlashasil.uz.Enums.ArticleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleChangeStatusDto {
    @NotNull(message = "Status required")//NOTBLANK------> Stringlar uchun
    private ArticleStatus status;
}