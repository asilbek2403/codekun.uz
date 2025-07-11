package dasturlashasil.uz.entities;


import dasturlashasil.uz.Enums.ProfileStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum profileStatus=ProfileStatusEnum.ACTIVE;
    @Column
    private String photo_id;
    @Column
    private Boolean visible=true;
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;

//    @OneToMany(mappedBy = "profileRoles")
//    private List<ProfileRoleEntity> roleList;

    @OneToMany(mappedBy = "profile") //  to‘g‘risi shu
    private List<ProfileRoleEntity> roleList;

    //Aarticleda mappedby bilan ishlatamiz
    @OneToMany(mappedBy = "moderator")
    private List<ArticleEntity> moderatorArticleList; //  profile.id = article.moderator_id

    @OneToMany(mappedBy = "publisher")
    private List<ArticleEntity> publisherArticleList; //  profile.id = article.published_id

}

