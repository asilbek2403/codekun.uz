package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.ArticleSectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity,String> {

    @Query("select sectionId from ArticleSectionEntity where articleId = ?1")
    List<Integer> getSectionIdListByArticleId(String articleId);


    @Modifying
    @Transactional
    @Query("delete from ArticleSectionEntity arc where arc.articleId = ?1 and arc.sectionId = ?2 ")
    void deleteByArticleIdAndSectionId(String articleId, Integer section);



}
