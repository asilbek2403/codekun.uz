package dasturlashasil.uz.Dto;

import dasturlashasil.uz.Enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;

    private String imageId;
    private AttachDto image;

    private Integer regionId;

    private Integer moderatorId;

    private Integer publisherId;

    private ArticleStatus status;

    private Integer readTime; // in second
    private Integer viewCount; // in second
    private LocalDateTime publishedDate;


    private List<CategoryDto> categoryList;
    private List<SectionDto> sectionList;



}
