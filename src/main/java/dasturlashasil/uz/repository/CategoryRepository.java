package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.CategoryEntity;
import dasturlashasil.uz.mapperL.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Query("from CategoryEntity where visible = true order by orderNumber asc")
    List<CategoryEntity> getAllByOrderSorted();


    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);

    Optional<CategoryEntity> findByOrderNumber(Integer number);

    Optional<CategoryEntity> findByCategoryKey(String key);

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.categoryKey AS categoryKey " +
            "FROM CategoryEntity c " +
            " inner join ArticleCategoryEntity ace on ace.categoryId = c.id " +
            "WHERE ace.articleId = :articleId and c.visible = true order by c.orderNumber asc")
    List<CategoryMapper> getCategoryListByArticleIdAndLang(@Param("articleId") String articleId, @Param("lang") String lang);

}
