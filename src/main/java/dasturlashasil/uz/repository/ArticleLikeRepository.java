package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.ArticleLikeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, String> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileIdAndVisibleTrue(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("update ArticleLikeEntity  set visible = false, deletedDate = current_timestamp where articleId =?1 and profileId =?2 and visible = true")
    int deleteByArticleIdAndProfileId(String articleId, Integer profileId);
}
