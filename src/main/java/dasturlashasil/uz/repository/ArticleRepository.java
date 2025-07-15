package dasturlashasil.uz.repository;

import dasturlashasil.uz.Enums.ArticleStatus;
import dasturlashasil.uz.entities.ArticleEntity;
import dasturlashasil.uz.mapperL.ArticleShortInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity,String> {

    @Modifying
    @Transactional
//    @Query("delete from ArticleEntity where id =?1")
    @Query("Update ArticleEntity set visible=false where id=?1")
    int delete(String articleId);


    //change PUT
    @Transactional
    @Modifying
    @Query("Update ArticleEntity set status = ?2 where id =?1")
    int changeStatus(String articleId, ArticleStatus status);

    /*
    select from article ar where id in(select articleId from article_section where sectionId = 10)
    order by created_date desc limit 5 where visible = true

    select * from article ar
    inner join article_section ac on ac.articleId = ar.id
    where ac.sectionId = ?1 and ar.visible=true and status =? order by ar.created_date
      */
    // select * from article where id in (select article_id from article_section where section_id = 10)
    // order by created_date desc limit N
    // where visible = true

    // select * from article a
    // inner join article_section ac on ac.article_id = a.id
    // where ac.section_id = ? and a.visible = true and status = ?
    // order by a.created_date desc

    /*@Query("from ArticleEntity where id in (select  articleId from ArticleSectionEntity where sectionId = ?1) " +
            " and visible = true and status = 'PUBLISHED' " +
            " order by createdDate desc  limit ?2")*/

//    @Query("from ArticleEntity ad " +
//            "inner join ArticleSectionEntity sd on sd.articleId = ad.id "+
//            "where sd.sectionId = ?1 and visible = true and ad.status="PUBLISHED"+
//            "order by ad.createdDate desc limit= ?2")
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
        " from  ArticleEntity a " +
        " inner join ArticleSectionEntity ac on ac.articleId = a.id " +
        " where ac.sectionId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
        " order by a.createdDate desc  limit ?2")
        List<ArticleShortInfo> getBySectionId(Integer sectionId, int limit);




    // 6
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " where a.visible = true and a.status = 'PUBLISHED' and id not in ?1" +
            " order by a.createdDate desc  limit 12")
    List<ArticleShortInfo> getLastArticleListExceptGiven(List<String> exceptIdList);


    // 7
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " inner join ArticleCategoryEntity ac on ac.articleId = a.id " +
            " where ac.categoryId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createdDate desc  limit ?2")
    List<ArticleShortInfo> getLastNByCategoryId(Integer categoryId, int limit);

    // 8
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " where a.regionId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createdDate desc  limit ?2")
    List<ArticleShortInfo> getLastNByRegionId(Integer regionId, int limit);




    // 12
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " where a.id <> ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.viewCount desc  limit 4")
    List<ArticleShortInfo> mostRead4Article(String exceptArticleId);


    //  13. Increase Article View Count by Article Id
    @Transactional
    @Modifying
    @Query("Update ArticleEntity set viewCount = viewCount + 1 where id =?1")
    int increaseViewCount(String articleId);

    // share count-ni increase qiladi va oxirgi qiymatni return qiladi.
    @Query(value = "UPDATE article SET shared_count = shared_count + 1 WHERE id = ?1 RETURNING shared_count", nativeQuery = true)
    int incrementSharedCountAndGet(String articleId);




    @Query(value = "UPDATE article SET like_count = COALESCE(like_count,0) + 1 WHERE id = ?1 RETURNING like_count", nativeQuery = true)
    int incrementLikeCountAndGetLastLikeCount(String articleId);


}
//6




/*
    ArticleShortInfo
         id(uuid),title,description,image(id,url),published_date
 */
