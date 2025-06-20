package dasturlashasil.uz.service;

import dasturlashasil.uz.Dto.regionD.RegionDto;
import dasturlashasil.uz.Dto.regionD.RegionLangDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.entities.RegionEntity;
import dasturlashasil.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;
    //Create
    public RegionDto create(RegionDto regionDto) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setOrderNum(regionDto.getOrderNum());
        regionEntity.setDescription(regionDto.getDescription());
        regionEntity.setCreatedDate(regionDto.getCreatedDate());
        regionEntity.setKey(regionDto.getKey());
        regionEntity.setNameRu(regionDto.getNameRu());
        regionEntity.setNameEn(regionDto.getNameEn());
        regionEntity.setNameUz(regionDto.getNameUz());
        regionEntity.setVisible(regionDto.getVisible());

        regionRepository.save(regionEntity);

        RegionDto regionDto1 = new RegionDto();
        regionDto1.setOrderNum(regionDto.getOrderNum());
        regionDto1.setDescription(regionDto.getDescription());
        regionDto1.setCreatedDate(regionDto.getCreatedDate());
        regionDto1.setKey(regionDto.getKey());
        regionDto1.setNameRu(regionDto.getNameRu());
        regionDto1.setNameEn(regionDto.getNameEn());
        regionDto1.setNameUz(regionDto.getNameUz());
        regionDto1.setVisible(regionDto.getVisible());

        regionDto1.setId(regionEntity.getId());// ID saqlanganidan so‘ng DTOga o‘rnatiladi
        return regionDto1;


    }

    //Update

    public RegionDto update(Integer id , RegionDto regionDto) {

        Optional<RegionEntity> regionEntity = regionRepository.findById(id);
        if (regionEntity.isPresent()) {
            RegionEntity regionEntity1 = regionEntity.get();

            if(regionDto.getOrderNum() != null)   regionEntity1.setOrderNum(regionDto.getOrderNum());
            if(regionDto.getDescription() != null)    regionEntity1.setDescription(regionDto.getDescription());
            if(regionDto.getCreatedDate() != null)regionEntity1.setCreatedDate(regionDto.getCreatedDate());
            if(regionDto.getKey() != null)  regionEntity1.setKey(regionDto.getKey());
            if(regionDto.getNameRu() != null)    regionEntity1.setNameRu(regionDto.getNameRu());
            if(regionDto.getNameEn() != null)   regionEntity1.setNameEn(regionDto.getNameEn());
            if(regionDto.getNameUz() != null)   regionEntity1.setNameUz(regionDto.getNameUz());
            if(regionDto.getVisible() != null)  regionEntity1.setVisible(regionDto.getVisible());

            regionRepository.save(regionEntity1);
            RegionDto regionDto1 = new RegionDto();
            regionDto1.setOrderNum(regionDto.getOrderNum());
            regionDto1.setDescription(regionDto.getDescription());
//            regionDto1.setCreatedDate(regionDto.getCreatedDate()); api ga kerakmas
            regionDto1.setKey(regionDto.getKey());
            regionDto1.setNameRu(regionDto.getNameRu());
            regionDto1.setNameEn(regionDto.getNameEn());
            regionDto1.setNameUz(regionDto.getNameUz());
            regionDto1.setVisible(regionDto.getVisible());
//            regionDto1.setId(regionEntity1.getId());  api ga kerakmas
            return regionDto1;
        }
        return null;
    }

    //Delete
    public Boolean delete(Integer id) {
        Optional<RegionEntity> regionEntity = regionRepository.findById(id);
        if (regionEntity.isPresent()) {
            regionRepository.delete(regionEntity.get());
            return true;
        }
        return false;
    }

    //  getList ALL
    public List<RegionDto> getAll() {
        Iterable<RegionEntity> regionEntityList = regionRepository.findAll();
        List<RegionDto> regionDtoList = new ArrayList<>();

        regionEntityList.forEach(regionEntity -> regionDtoList.add(getDto(regionEntity)));
        return regionDtoList;


    }

    //Entitylarni >>> Dtolarga aylantiraman
    public RegionDto getDto(RegionEntity regionEntity) {
        RegionDto regionDto = new RegionDto();
        regionDto.setId(regionEntity.getId());
        regionDto.setOrderNum(regionEntity.getOrderNum());
        regionDto.setDescription(regionEntity.getDescription());
        regionDto.setCreatedDate(regionEntity.getCreatedDate());
        regionDto.setKey(regionEntity.getKey());
        regionDto.setNameRu(regionEntity.getNameRu());
        regionDto.setNameEn(regionEntity.getNameEn());
        regionDto.setNameUz(regionEntity.getNameUz());
        regionDto.setVisible(regionEntity.getVisible());
        return regionDto;
    }


    //tilni berib yuborsa data shu tilda berilishi

    public List<RegionLangDto> getAllLang(LanguageList lana) {

        List<RegionLangDto> regionLangDtoList = new ArrayList<>();

        List<RegionEntity> regionEntityList= regionRepository.findAllByVisibleTrue();

        for ( RegionEntity regionEntity : regionEntityList) {
            RegionLangDto regionLangDto = new RegionLangDto();
            regionLangDto.setId(regionEntity.getId());
            regionLangDto.setKey(regionEntity.getKey());
            switch (lana) {
                case UZ -> regionLangDto.setName(regionEntity.getNameUz());
                case RU -> regionLangDto.setName(regionEntity.getNameRu());
                case EN -> regionLangDto.setName(regionEntity.getNameEn());
            }
            regionLangDtoList.add(regionLangDto);

        }
        return regionLangDtoList;
    }



}
