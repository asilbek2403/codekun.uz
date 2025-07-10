package dasturlashasil.uz.repository;


import dasturlashasil.uz.Dto.ArticleFilterDTO;
import dasturlashasil.uz.Dto.FilterResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Repository
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDto<Object[]> filter(ArticleFilterDTO filter, int page, int size) {

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT a.id, a.title, a.description, a.publishedDate,a.imageId FROM ArticleEntity a ");
        // 1 - N
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(a) FROM ArticleEntity a ");

        StringBuilder builder = new StringBuilder(" where a.visible = true and a.status = 'PUBLISHED' ");

        Map<String, Object> params = new HashMap<>();

        if (filter.getTitle() != null) {
            builder.append(" and  lower(a.title) like :title ");
            params.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }
        if (filter.getRegionId() != null) {
            builder.append(" and  a.regionId =:regionId");
            params.put("regionId", filter.getRegionId());
        }
        if (filter.getSectionId() != null) {
            selectQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");

            builder.append(" and  ase.sectionId =:sectionId");
            params.put("sectionId", filter.getSectionId());
        }
        // category TODO
        if (filter.getCreatedDateTo() != null && filter.getCreatedDateFrom() != null) {
            builder.append(" and a.publishDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateFrom() != null) {
            builder.append(" and a.publishDate > :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            builder.append(" and a.publishDate < :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
//        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());//entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        List<Object[]> articleList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new FilterResultDto<>(articleList, totalElements);
    }


}
