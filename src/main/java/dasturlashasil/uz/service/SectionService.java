package dasturlashasil.uz.service;


import dasturlashasil.uz.Dto.LangResponseDto;
import dasturlashasil.uz.Dto.SectionDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.entities.SectionEntity;
import dasturlashasil.uz.exceptons.NotFoundException;
import dasturlashasil.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;


    public SectionDto create(SectionDto dto){
        Optional<SectionEntity> optional = sectionRepository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()) {
//            throw new AppBadException("OrderNumber " + dto.getOrderNumber() + " already exist");
            return null;
        }
        SectionEntity entity = new SectionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setSectionKey(dto.getSectionKey());
        entity.setCreatedDate(LocalDateTime.now());
//        entity.setImageId(dto.getImageId());
        sectionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public SectionDto update(Integer id, SectionDto newDto){
        Optional<SectionEntity> optional = sectionRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Section not found");

        }
        SectionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setSectionKey(newDto.getSectionKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreatedDate());
//        newDto.setImageId(entity.getImageId());
        sectionRepository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id) {
        var entity = sectionRepository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Section not found"));
        int i = sectionRepository.updateVisibleById(entity.getId());
        return i == 1;
    }

    public List<SectionDto> getAllByOrder() {
        Iterable<SectionEntity> iterable = sectionRepository.getAllByOrderSorted();
        List<SectionDto> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDto> getAllbyLang(LanguageList lang){
        Iterable<SectionEntity> iterable = sectionRepository.getAllByOrderSorted();
        List<LangResponseDto> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private SectionDto toDto(SectionEntity entity) {
        SectionDto dto = new SectionDto();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setSectionKey(entity.getSectionKey());
        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setImageId(entity.getImageId());
        return dto;
    }

    private LangResponseDto toLangResponseDto(LanguageList lang, SectionEntity entity){
        LangResponseDto dto = new LangResponseDto();
        dto.setId(entity.getId());
        dto.setKey(entity.getSectionKey());
        switch (lang){
            case UZ:
                dto.setName(entity.getNameUz());
                break;
            case RU:
                dto.setName(entity.getNameRu());
                break;
            case EN:
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }



}

