package dasturlashasil.uz.Dto.article;

import dasturlashasil.uz.Enums.LikeEmotion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleLikeCreateDto {

        @NotBlank(message = "ArticleId required")
        private String articleId;

        @NotNull(message = "Emotion required")
        private LikeEmotion emotion;


}
