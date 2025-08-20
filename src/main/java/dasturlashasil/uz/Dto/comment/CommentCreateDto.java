package dasturlashasil.uz.Dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {
    @NotBlank(message = "Content required")
    private String content;
    @NotBlank(message = "ArticleId required")
    private String articleId;
    private String replyId;
}