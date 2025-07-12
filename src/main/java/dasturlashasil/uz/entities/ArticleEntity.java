package dasturlashasil.uz.entities;


import dasturlashasil.uz.Enums.ArticleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
            private String id;
    @Column(name = "title" , columnDefinition = "text")
    private String title;
    @Column(name="description" , columnDefinition = "text")
    private String description;
    @Column(name="content" , columnDefinition = "text")
    private String content;
    @Column(name="shared_count")
    private Long sharedCount;
    @Column(name="like_count")
    private Long likeCount = 0l;
    @Column(name = "dislike_count")
    private Long dislikeCount = 0l;



    @Column(name= "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)// buyerda OneToOne ham bo'lishi mumkun.
    @JoinColumn(name= "image_id" , insertable = false,updatable = false)
    private AttachEntity image;
//#####################

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="region_id" , insertable = false,updatable = false)
    private RegionEntity region;

//##############        ManytToMany
    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;
/// //// moderator id backend ichida qilinadi
    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;
    /// //// publisherId  backend ichida qilinadi


    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ArticleStatus status;

    @Column(name = "read_time")
    private Integer readTime; // in second

    @Column(name = "view_count")
    private Integer viewCount; // in second

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    //  (Bitta article bir-nechta Category-ga tegishli mumkun)
    // 1 - N
    // N - 1
    //  (Bitta article bir-nechta Section-da bo'lishi mumkun. Masalan Asosiy,Muharrir h.k.)
//    Article
//    id(uuid),title,description,content,shared_count,image_id,
//    region_id,moderator_id,publisher_id,status(Published,NotPublished),
//    readTime (maqolani nechi daqiqa o'qilishi)
//                      created_date,published_date,visible,view_count
//    (Bitta article bitta Region-ga tegishli mumkun (ixtiyoriy))
//            (Bitta article bir-nechta Category-ga tegishli mumkun)
//            (Bitta article bir-nechta Section-da bo'lishi mumkun. Masalan Asosiy,Muharrir h.k.)




}
