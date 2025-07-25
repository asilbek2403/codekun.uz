package dasturlashasil.uz.Dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlashasil.uz.Dto.article.ArticleDTO;
import dasturlashasil.uz.Dto.profile.ProfileDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private String id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProfileDto profile;
    private ArticleDTO article;
    private CommentDto reply;
    private Long likeCount;
    private Long disLikeCount;
}