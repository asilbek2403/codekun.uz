package dasturlashasil.uz.service;

import dasturlashasil.uz.Dto.article.ArticleCreateDto;
import dasturlashasil.uz.Dto.article.ArticleDTO;
import dasturlashasil.uz.Dto.article.ArticleFilterDTO;
import dasturlashasil.uz.Dto.FilterResultDto;
import dasturlashasil.uz.Enums.ArticleStatus;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.entities.ArticleEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.mapperL.ArticleShortInfo;
import dasturlashasil.uz.repository.ArticleCustomRepository;
import dasturlashasil.uz.repository.ArticleRepository;
import dasturlashasil.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
//import java.util.ArrayList;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleSectionService articleSectionService;


    @Autowired
    private AttachService attachService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SectionService sectionService;


    @Autowired
    private ArticleCustomRepository articleCustomRepository;



    public  ArticleDTO  create(ArticleCreateDto articleCreateDto) {
    ArticleEntity articleEntity = new ArticleEntity();
    toEntity( articleCreateDto , articleEntity);
        // Set default values

        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleEntity.setCreatedDate(LocalDateTime.now());
        articleEntity.setVisible(true);
        articleEntity.setViewCount(0);
        articleEntity.setSharedCount(0L);
        articleEntity.setModeratorId(SpringSecurityUtil.currentProfileId());//Hozirgi murojaat profile id sini
        ArticleEntity savedEntity = articleRepository.save(articleEntity);
        // category -> merge
        articleCategoryService.merge(savedEntity.getId(), articleCreateDto.getCategoryList());
        // section -> merge
        // return
        articleSectionService.merge(savedEntity.getId() , articleCreateDto.getSectionList());


    return toDTO(articleEntity);

    }
    // 1
//    public ArticleDTO create(ArticleCreateDTO createDTO) {
//        ArticleEntity entity = new ArticleEntity();
//        toEntity(createDTO, entity);
//        // Set default values
//        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
//        entity.setCreatedDate(LocalDateTime.now());
//        entity.setVisible(true);
//        entity.setViewCount(0);
//        entity.setSharedCount(0L);
//        entity.setModeratorId(SpringSecurityUtil.currentProfileId());
//        // save
//        articleRepository.save(entity);
//        // category -> merge
//        articleCategoryService.merge(entity.getId(), createDTO.getCategoryList());
//        // section -> merge
//        articleSectionService.merge(entity.getId(), createDTO.getSectionList());
//        // return
//        return toDTO(entity);
//    }

    private void toEntity(ArticleCreateDto dto, ArticleEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
    }
    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setStatus(entity.getStatus());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    public ArticleDTO update(String articleId,ArticleCreateDto dto) {

        ArticleEntity entity = get(articleId);
        toEntity(dto, entity);
        // update qilganini save qilamiz
        articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(entity.getId(), dto.getCategoryList());
        // section -> merge
        articleSectionService.merge(entity.getId(), dto.getSectionList());
        // return
        return toDTO(entity);
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }


//    public String delete(String articleId) {
//

    //delete
    /*
    public String delete(String articleId) {
//        // 1. Maqolani bazadan topamiz
//        ArticleEntity entity = articleRepository.findById(articleId)
//                .orElseThrow(() -> new AppBadException("Article not found"));
//        // 2. Bog‘liqliklarni (ManyToMany) tozalaymiz
//        entity.setVisible( Boolean.FALSE); // article_category tozalanadi
//        entity.setSectionList(new ArrayList<>());  // article_section tozalanadi
//        articleRepository.save(entity);            // Tozalangan holatini saqlaymiz
//        // 3. Rasm bo‘lsa, uni ham o‘chiramiz
//        if (entity.getImage() != null) {
//            attachService.sremove(entity.getImage().getId()); // Faylni diskdan va bazadan o‘chirish
//        }
//        // 4. ArticleEntity’ni o‘chiramiz
//        articleRepository.delete(entity);
//        return "Article deleted successfully";
//    }
        ArticleEntity entity = get(articleId);
        if( entity.getImage() != null){
            attachService.sremove(entity.getImageId());
            return "Image Deleted successfully";
        }
//            // 1. ArticleEntity ni topamiz
//            ArticleEntity entity = articleRepository.findById(articleId)
//                    .orElseThrow(() -> new AppBadException("Article not found"));
//
//            // 2. Bog'liqliklarni tozalaymiz
//            entity.s(new ArrayList<>()); // article_category bo'shatiladi
//            entity.setSectionList(new ArrayList<>());  // article_section bo'shatiladi
//
//            // 3. Rasmni o‘chiramiz
//            if (entity.getImage() != null) {
//                attachService.sremove(entity.getImageId());
//            }
//
//            // 4. Endi delete by ID
//
        int effected = articleRepository.delete(articleId);
        if (effected > 0) {
            return "Article deleted";
        } else {
            return "Something went wrong";
        } */
    public String delete(String articleId) {
        int effectedRows = articleRepository.delete(articleId);
        if (effectedRows > 0) {
            return "Article deleted";
        } else {
            return "Something went wrong";
        }
//        return "Deleted successfully";
    }



    public String changeStatus(String articleId, ArticleStatus status) {
        int effectedRows = articleRepository.changeStatus(articleId, status);
        if (effectedRows > 0) {
            return "Article status change";
        } else {
            return "Something went wrong";
        }
}

    public List<ArticleDTO> getByIdSection(Integer sectionId, int limit) {
        List<ArticleShortInfo> result =articleRepository.getBySectionId(sectionId,limit);

        List<ArticleDTO> responsList = new LinkedList<>();
        result.forEach(mapper ->responsList.add(toDTO(mapper)));

        return responsList;
    }



    private ArticleDTO toDTO(ArticleShortInfo dtoMapper){
        ArticleDTO dto = new ArticleDTO();
        dto.setId(dtoMapper.getId());
        dto.setTitle(dtoMapper.getTitle());
        dto.setDescription(dtoMapper.getDescription());
        dto.setImage(attachService.openDTO(dtoMapper.getId()));
        dto.setPublishedDate(dtoMapper.getPublishedDate());
            return dto;
    }

    /*
    Get Last 12 published Article except given id list ordered_by_created_date
        ([1,2,3,])
     */
//6
//    private List<ArticleDTO> getLast12List(List<String> exlist){
//        List<ArticleShortInfo> exres12= articleRepository.getLastArticleListExceptGiven(exlist);
//        List<ArticleDTO> responseList = new LinkedList<>();
//        exres12.forEach(mapper -> responseList.add(toDTO(mapper)));
//        return responseList;
//    }
    public List<ArticleDTO> getLast12xList(List<String> exceptIdList) { // 1, 100
        List<ArticleShortInfo> resultList = articleRepository.getLastArticleListExceptGiven(exceptIdList);
        List<ArticleDTO> responseList = new LinkedList<>();
        resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
        return responseList;
    }
    // 7
    public List<ArticleDTO> getLastNArticleByCategoryId(Integer categoryId, Integer limit) {

            List<ArticleShortInfo> resultList = articleRepository.getLastNByCategoryId(categoryId, limit);
            List<ArticleDTO> responseList = new LinkedList<>();
            resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
            return responseList;


    }

    // 8
    public List<ArticleDTO> getLastNArticleByRegionId(Integer regionId, Integer limit) {
        List<ArticleShortInfo> resultList = articleRepository.getLastNByRegionId(regionId, limit);
        List<ArticleDTO> responseList = new LinkedList<>();
        resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
        return responseList;
    }

    //9
    public ArticleDTO getById(String id, LanguageList lang) {
        // get
        ArticleEntity entity = get(id);

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setLikeCount(entity.getLikeCount());
        // articleLikeService.getArticleLikeCount(entity.getId()); select count(*) from article_lik where article_id = ? and emotion = 'KILE';
        // set region
        dto.setRegion(regionService.getByIdAndLang(entity.getRegionId(), lang));
        // set section   ->  1 -N
        dto.setSectionList(sectionService.getSectionListByArticleIdAndLang(entity.getId(), lang));
        // category
        dto.setCategoryList(categoryService.getCategoryListByArticleIdAndLang(entity.getId(), lang));
        // tag
        return dto;
    }



    // 5 -
    public List<ArticleDTO> getBySectionId(Integer sectionId, int limit) { // 1, 100
        List<ArticleShortInfo> resultList = articleRepository.getBySectionId(sectionId, limit);
        List<ArticleDTO> responseList = new LinkedList<>();
        resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
        return responseList;
    }


    //   12. Get 4 most read articles, except given article id .
    public List<ArticleDTO> getMostRead4ArticleExceptGivenId(String exceptArticleId) {
        List<ArticleShortInfo> resultList = articleRepository.mostRead4Article(exceptArticleId);
        List<ArticleDTO> responseList = new LinkedList<>();
        resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
        return responseList;
    }


    //  13. Increase Article View Count by Article Id
    public Boolean increaseViewCount(String articleId) {
        // Qaysi IP orqali view qilinganini yozib qo'ysak yaxshi bo'lar edi, Shu IP dan shu maqolani o'qishdi deb.
        articleRepository.increaseViewCount(articleId);
        return Boolean.TRUE;
    }


//  14. Increase Article Shared Count by Article Id
    public Integer increaseSharedCount(String articleId) {
        int sharedCount = articleRepository.incrementSharedCountAndGet(articleId);
        return sharedCount;
    }

    // 15
    public Page<ArticleDTO> filter(ArticleFilterDTO filter, int page, int size) { // 1, 100
        FilterResultDto<Object[]> filterResult = articleCustomRepository.filter(filter, page, size);
        List<ArticleDTO> articleList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            ArticleDTO article = new ArticleDTO();
            // a.id, a.title, a.description, a.publishedDate,a.imageId
            article.setId((String) obj[0]);
            article.setTitle((String) obj[1]);
            article.setDescription((String) obj[2]);
            article.setPublishedDate((LocalDateTime) obj[3]);
            if (obj[4] != null) {
                article.setImage(attachService.openDTO((String) obj[4]));
            }
            articleList.add(article);
        }
        return new PageImpl<>(articleList, PageRequest.of(page, size), filterResult.getTotal());
    }


}

//1. CREATE (Moderator) status(NotPublished)
//Request: (title,description,content,imageId, regionId, categoryList[{id},{id}], sectionList[{id},{id}] )
//Response: ArticleInfoDTO
//    2. Update (Moderator (status to not publish)) (remove old image)
//Request: (title,description,content,imageId, regionId, categoryList[], sectionList[])
//Response: ArticleInfoDTO
//    3. Delete Article (MODERATOR)
//Request: id
//Response: "Message"
//        4. Change status by id (PUBLISHER) (publish,not_publish)
//Request: id, (status)
//Response: "Message"