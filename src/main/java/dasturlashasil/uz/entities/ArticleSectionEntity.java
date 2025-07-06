package dasturlashasil.uz.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_section")
public class ArticleSectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;//o'zidagi

    @Column(name = "section_id")
    private Integer sectionId;  //o'zidagi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", insertable = false, updatable = false)
    private SectionEntity section;

    @Column(name = "article_id")
    private String articleId;//o'zidagi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;


}