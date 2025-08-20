package dasturlashasil.uz.mapperL;


import java.time.LocalDateTime;

public interface CommentMapper {
    String getId();

    String getContent();

    LocalDateTime getCreatedDate();

    LocalDateTime getUpdatedDate();

    Integer getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getProfilePhotoId();

    Long getLikeCount();

    Long getDislikeCount();

}