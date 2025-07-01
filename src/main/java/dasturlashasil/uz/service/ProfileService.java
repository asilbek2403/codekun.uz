package dasturlashasil.uz.service;


import dasturlashasil.uz.Dto.profile.ProfileFilterDto;
import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.Enums.ProfileStatusEnum;
import dasturlashasil.uz.entities.ProfileEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.repository.ProfileCustomRepository;
import dasturlashasil.uz.repository.ProfileRepository;
import dasturlashasil.uz.Dto.FilterResultDto;
import dasturlashasil.uz.util.MapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileDto create(ProfileDto profile) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(profile.getUsername());
        if (optional.isPresent()) {
            throw new AppBadException("User exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profile.getName());
        entity.setSurname(profile.getSurname());
//        entity.setPassword(profile.getPassword());// bu ByCrypt siz hold

        entity.setPassword(bCryptPasswordEncoder.encode(profile.getPassword()));
        // TODO MD5/ByCript

        entity.setUsername(profile.getUsername());
        entity.setProfileStatus(ProfileStatusEnum.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        profileRepository.save(entity); // save

        // role_save
        profileRoleService.merge(entity.getId(), profile.getRoleList()); // TODO understand

        // result
        ProfileDto response = toDTO(entity);
        response.setRoleList(profile.getRoleList());
        return response;
    }


    public ProfileDto update(Integer id, @Valid ProfileDto dto) {
        ProfileEntity entity = get(id);
        // check username exists
        Optional<ProfileEntity> usernameOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (usernameOptional.isPresent() && !usernameOptional.get().getId().equals(id)) {
            throw new AppBadException("Username exists");
        }
        //
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        profileRepository.save(entity); // update
        // role_save
        profileRoleService.merge(entity.getId(), dto.getRoleList());
        // result
        ProfileDto response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }






    //v2 P
    public PageImpl<ProfileDto> filter(ProfileFilterDto filterDTO, int page, int size) {
        FilterResultDto<Object[]> result = profileCustomRepository.filter(filterDTO, page, size);
        List<ProfileDto> profileDTOList = new LinkedList<>();
        result.getContent().forEach(entity -> profileDTOList.add(toDTO(entity)));
        return new PageImpl<>(profileDTOList, PageRequest.of(page, size), result.getTotal());
    }// endi filter uvchunResultDto



    public ProfileDto toDTO(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDto toDTO(Object[] mapper) {
        ProfileDto dto = new ProfileDto();
        dto.setId((Integer) mapper[0]);
        dto.setName((String) mapper[1]);
        dto.setSurname((String) mapper[2]);
        dto.setUsername((String) mapper[3]);
        if (mapper[4] != null) {
            dto.setStatus(ProfileStatusEnum.valueOf((String) mapper[4]));
        }
        dto.setCreatedDate(MapperUtil.localDateTime(mapper[5]));
        // String[] → List<ProfileRoleEnum>
        String[] roles = (String[]) mapper[6];
        if (roles != null) {
            List<ProfileRoleEnum> roleList = Arrays.stream(roles)
                    .map(ProfileRoleEnum::valueOf)
                    .collect(Collectors.toList());
            dto.setRoleList(roleList);
        } else {
            dto.setRoleList(Collections.emptyList());
        }
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findByIdAndVisibleIsTrue(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });
        /*Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        return optional.get();*/
    }

    public Page<ProfileDto> getAllProfiles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<ProfileEntity> entityPage=profileRepository.findAllByVisibleTrue(pageable);

        List<ProfileDto> dtoList=new ArrayList<>();
        entityPage.forEach(profileEntity -> dtoList.add(toDTO(profileEntity)));


        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());

    }


    public Boolean delete(Integer id) {
        ProfileEntity entity = get(id);  // mavjud profilni olib keladi
        entity.setVisible(false);        // soft delete
        profileRepository.save(entity);
        return true;
    }


    //filter
//filterlar
    //mmmmmmm
//    public Page<ProfileDto> filter(ProfileFilterDto filter, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

    //;;;;;
//    Page<ProfileEntity> entityPage = profileCustomRepository.filter(filter, pageable);

//    List<ProfileDto> dtoList = entityPage.getContent().stream()
//            .map(this::toDTO)
//            .collect(Collectors.toList());
//
//    return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
//
//        /';./
//        return null;
//    }



//    Profile da yo'q bizga LAng kerakmas!!!
//    public List<ProfileDto> getAllLang(LanguageList lana) {
//
//        List<ProfileDto> profileLangDtoList = new ArrayList<>();
//
//        List<ProfileEntity> profileEntityList= profileRepository.findAllByVisibleTrue();
//
//        for ( ProfileEntity regionEntity : profileEntityList) {
//            ProfileDto regionLangDto = new ProfileDto();
//            regionLangDto.setId(regionEntity.getId());
//            switch (lana) {
//                case UZ -> regionLangDto.setName(regionEntity.getNameUz());
//                case RU -> regionLangDto.setName(regionEntity.getNameRu());
//                case EN -> regionLangDto.setName(regionEntity.getNameEn());
//            }
//            profileLangDtoList.add(regionLangDto);
//
//        }
//        return regionLangDtoList;
//    }


    public PageImpl<ProfileDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<ProfileEntity> result = profileRepository.findAllWithRoles(pageable);

        List<ProfileDto> dtoList = new ArrayList<>();
        for(ProfileEntity profile : result.getContent() ){
            dtoList.add(toDTO(profile));}
        return new PageImpl<>(dtoList,pageable,result.getTotalElements());
        }


}

