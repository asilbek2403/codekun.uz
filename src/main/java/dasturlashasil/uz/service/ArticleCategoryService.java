package dasturlashasil.uz.service;

import dasturlashasil.uz.Dto.CategoryDto;
import dasturlashasil.uz.entities.ArticleCategoryEntity;
import dasturlashasil.uz.repository.ArticleCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleCategoryService {


        @Autowired
        private ArticleCategoryRepository articleCategoryRepository;

        public void merge(String articleId, List<CategoryDto> dtoList) {
            List<Integer> newList = dtoList.stream().map(CategoryDto::getId).toList();

            List<Integer> oldList = articleCategoryRepository.getCategoryIdListByArticleId(articleId);
            newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(articleId, pe)); // create
            oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> articleCategoryRepository.deleteByCategoryIdAndArticleId(articleId, pe));
        }

        public void create(String articleId, Integer categoryId) {
            ArticleCategoryEntity entity = new ArticleCategoryEntity();
            entity.setArticleId(articleId);
            entity.setCategoryId(categoryId);
            articleCategoryRepository.save(entity);
        }
}
