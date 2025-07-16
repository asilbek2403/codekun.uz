package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.article.*;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.service.ArticleService;
import dasturlashasil.uz.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    public ResponseEntity<ArticleDTO> create(@RequestParam("file") ArticleCreateDto dto) {


    // 1. CREATE (Moderator) status(NotPublished)
    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@RequestBody @Valid ArticleCreateDto dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }
// /api/v1/article -> permitAll()



    // 2. Update (Moderator (status to not publish)) (remove old image)
    @PutMapping("/moderator/{articleId}")
    public ResponseEntity<ArticleDTO> update(@PathVariable("articleId") String articleId,
                                        @RequestBody @Valid ArticleCreateDto dto) {
        return  ResponseEntity.ok(articleService.update(articleId, dto));
    }
    //delete
    @DeleteMapping("/moderator/{articleId}")
    public ResponseEntity<String > deleten(@PathVariable("articleId") String articleId ){
        return  ResponseEntity.ok(articleService.delete(articleId));
    }
    // 4. Change status by id (PUBLISHER) (publish,not_publish)
    @PutMapping("/moderator/{articleId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("articleId") String articleId,
    @RequestBody @Valid ArticleChangeStatusDto statusDTO) {
        return ResponseEntity.ok(articleService.changeStatus(articleId, statusDTO.getStatus()));
    }
    // 5. Get Last N Article By sectionId  ordered_by_created_date desc
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ArticleDTO>> getListsection(@PathVariable("sectionId") Integer sectionId,
                                                           @RequestParam(value = "limit" , defaultValue = "2") int limit ){
        return ResponseEntity.ok(articleService.getByIdSection(sectionId , limit));
    }//respone --------    ArticleShortInfo
//    id(uuid),title,description,image(id,url),published_date


//    @GetMapping("/last-12")
//    public ResponseEntity<List<ArticleDTO>> getLast12Articles(
//            @RequestParam List<String> exceptIdList) {
//        return ResponseEntity.ok(articleService.getLast12List(exceptIdList));
//    }
//6
// 6. Get Last 12 published Article except given id list ordered_by_created_date
                //6
    @PostMapping("/last-12")
public ResponseEntity<List<ArticleDTO>> getLast12(@Valid @RequestBody Last12ArticleDTO dto) {
    return ResponseEntity.ok(articleService.getLast12xList(dto.getExceptArticleIdList()));
}
            //    //  7. Get Last N Article by categoryId.
     @GetMapping("/by-category/{categoryId}/{limit}")
            public ResponseEntity<List<ArticleDTO>> lastNArticleByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                                                             @PathVariable("limit") Integer limit) {
                return ResponseEntity.ok(articleService.getLastNArticleByCategoryId(categoryId, limit));
            }


    //  8. Get Last N Article by regionId.
    @GetMapping("/by-region/{regionId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> lastNArticleByRegionId(@PathVariable("regionId") Integer regionId,
                                                                   @PathVariable("limit") Integer limit) {
        return ResponseEntity.ok(articleService.getLastNArticleByRegionId(regionId, limit));
    }


    //9. Get Article By Id And Lang
    //        ArticleFullInfo
    @GetMapping("/detail/{articleId}")
    public ResponseEntity<ArticleDTO> getDetail(@PathVariable("articleId") String articleId,
                                                @RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageList language) {
        return ResponseEntity.ok(articleService.getById(articleId, language));
    }
        /*10. Get Last N Article By TagName (Berilgan tag nomi bo'yicha oxirgi N-ta yangilikni return qiladi)
            (Pagination bilan ishlaydigan qilish kerak)
    ArticleShortInfo*/

    // 11. Get Last 4 Article By sectionId, except given article id.
    @GetMapping("/section-small/{sectionId}")
    public ResponseEntity<List<ArticleDTO>> last4ArticleBySectionId(@PathVariable("sectionId") Integer sectionId,
                                                                    @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return ResponseEntity.ok(articleService.getBySectionId(sectionId, 4));
    }


    //   12. Get 4 most read articles, except given article id . (Ko'p oqilgan oxirgi 4-ta yangilikni return qiladi, berilgan maqoldagan tashqari)
    @GetMapping("/most-read/{exceptArticleId}")
    public ResponseEntity<List<ArticleDTO>> mostReadArticles(@PathVariable("exceptArticleId") String sectionId) {
        return ResponseEntity.ok(articleService.getMostRead4ArticleExceptGivenId(sectionId));
    }




    // 13. Increase Article View Count by Article Id
    @GetMapping("/view-count/{articleId}")
    public ResponseEntity<Boolean> increaseViewCount(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.increaseViewCount(articleId));
    }



    // 14. Increase Share Count by Article Id
    @GetMapping("/shared-count/{articleId}")
    public ResponseEntity<Integer> increaseSharedCount(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.increaseSharedCount(articleId));
    }

    //  15. Filter Article
    @PostMapping("/filter")
    public ResponseEntity<Page<ArticleDTO>> filter(@RequestBody ArticleFilterDTO filter,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.filter(filter, PageUtil.page(page), size));
    }



}
