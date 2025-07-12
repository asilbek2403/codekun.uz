package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity , String> {

    @Query("select categoryId from ArticleCategoryEntity where articleId =?1")
    List<Integer> getCategoryIdListByArticleId(String articleId);

    @Modifying
    @Transactional
//    @Query("delete  from  ArticleCategoryEntity where articleId =?1 and categoryId =?2")
    @Query("delete  from  ArticleCategoryEntity where articleId =?1 and categoryId =?2")
    void deleteByCategoryIdAndArticleId(String articleId, Integer categoryId);

}
