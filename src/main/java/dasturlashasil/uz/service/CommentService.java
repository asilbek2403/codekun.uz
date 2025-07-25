package dasturlashasil.uz.service;





import dasturlashasil.uz.Dto.FilterResultDto;
import dasturlashasil.uz.Dto.article.ArticleDTO;
import dasturlashasil.uz.Dto.comment.CommentCreateDto;
import dasturlashasil.uz.Dto.comment.CommentDto;
import dasturlashasil.uz.Dto.comment.CommentFilterDto;
import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.entities.CommentEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.mapperL.CommentMapper;
import dasturlashasil.uz.repository.CommentCustomRepository;
import dasturlashasil.uz.repository.CommentRepository;
import dasturlashasil.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CommentCustomRepository commentCustomRepository;

    public CommentDto create(CommentCreateDto dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setReplyId(dto.getReplyId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfileId(SpringSecurityUtil.currentProfileId());
        // save
        commentRepository.save(entity);
        // response
        return toDto(entity);
    }

    public CommentDto update(String id, CommentCreateDto dto) {
        CommentEntity entity = get(id);
        if (!entity.getProfileId().equals(SpringSecurityUtil.currentProfileId())) {
            throw new AppBadException("You can not update this article");
        }
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
        commentRepository.save(entity);
        return toDto(entity);
    }

    public Boolean delete(String id) {
        CommentEntity comment = get(id);
        if (!SpringSecurityUtil.hasAnyRoles(ProfileRoleEnum.ROLE_ADMIN)) {
            if (!comment.getProfileId().equals(SpringSecurityUtil.currentProfileId())) {
                throw new AppBadException("You can not delete this article");
            }
        }
        return commentRepository.updateVisibleById(id) == 1;
    }

    public List<CommentDto> commentRepliedList(String commentId) {
        List<CommentMapper> pageObj = commentRepository.repliedCommentList(commentId);
        List<CommentDto> dtoList = new LinkedList<>();
        pageObj.forEach(mapper -> dtoList.add(toDto(mapper)));
        return dtoList;
    }

    public Page<CommentDto> filter(CommentFilterDto filter, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        FilterResultDto<Object[]> filterResult = commentCustomRepository.filter(filter, page, size);
        List<CommentDto> commentList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            CommentDto comment = new CommentDto();
            comment.setId((String) obj[0]);
            comment.setContent((String) obj[1]);
            comment.setCreatedDate((LocalDateTime) obj[2]);
            comment.setUpdatedDate((LocalDateTime) obj[3]);
            comment.setLikeCount((Long) obj[4]);
            comment.setDisLikeCount((Long) obj[5]);
            if (obj[6] != null) {
                CommentDto reply = new CommentDto();
                reply.setId((String) obj[6]);
                comment.setReply(reply);
            }

            ProfileDto profile = new ProfileDto();
            profile.setId((Integer) obj[7]);
            profile.setName((String) obj[8]);
            profile.setSurname((String) obj[9]);
            if (obj[10] != null) {
                profile.setPhotoId(attachService.openDTO((String) obj[10]));
            }
            ArticleDTO article = new ArticleDTO();
            article.setId((String) obj[11]);
            article.setTitle((String) obj[12]);
            comment.setArticle(article);

            commentList.add(comment);
        }
        return new PageImpl<>(commentList, PageRequest.of(page, size), filterResult.getTotal());
    }

    public Page<CommentDto> getAllByArticleId(String articleId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommentMapper> pageObj = commentRepository.findByArticleId(articleId, pageRequest);
        List<CommentDto> dtos = new LinkedList<>();
        pageObj.forEach(mapper -> dtos.add(toDto(mapper)));
        return new PageImpl<>(dtos, pageRequest, pageObj.getTotalElements());
    }

    private CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        if (entity.getReplyId() != null) {
            CommentDto reply = new CommentDto();
            reply.setId(entity.getId());
            dto.setReply(reply);
        }
//        dto.setProfile();
        return dto;
    }

    private CommentDto toDto(CommentMapper mapper) {
        CommentDto dto = new CommentDto();
        dto.setId(mapper.getId());
        dto.setContent(mapper.getContent());
        dto.setCreatedDate(mapper.getCreatedDate());
        dto.setUpdatedDate(mapper.getUpdatedDate());
        dto.setLikeCount(mapper.getLikeCount());
        dto.setDisLikeCount(mapper.getLikeCount());

        ProfileDto profile = new ProfileDto();
        profile.setId(mapper.getProfileId());
        profile.setName(mapper.getProfileName());
        profile.setSurname(mapper.getProfileSurname());
        if (mapper.getProfilePhotoId() != null) {
            profile.setPhotoId(attachService.openDTO(mapper.getProfilePhotoId()));
        }
        dto.setProfile(profile);

        return dto;
    }

    public CommentEntity get(String id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Item not found");
        });
    }
}
