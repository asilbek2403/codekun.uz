package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.article.ArticleLikeCreateDto;
import dasturlashasil.uz.service.ArticleLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article-like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("") // Like and Dislike
    public ResponseEntity<Void> create(@Valid @RequestBody ArticleLikeCreateDto dto) {
        articleLikeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remove/{articleId}")
    public ResponseEntity<Boolean> remove(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.remove(articleId));
    }
}
