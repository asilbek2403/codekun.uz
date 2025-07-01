package dasturlashasil.uz.service;


import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.entities.ProfileRoleEntity;
import dasturlashasil.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void create(Integer profileId, List<ProfileRoleEnum> roleList) {
        for (ProfileRoleEnum roleEnum : roleList) {
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRoles(roleEnum);
            profileRoleRepository.save(entity);
        }

    }

    public void merge(Integer profileId, List<ProfileRoleEnum> newList) {
        List<ProfileRoleEnum> oldList = profileRoleRepository.getRoleListByProfileId(profileId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(profileId, pe)); // create
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe-> profileRoleRepository.deleteByIdAndRoleEnum(profileId, pe));
    }

    // old -> "ROLE_USER", "ROLE_ADMIN"
    // new -> "ROLE_USER", "ROLE_MODERATOR"

//    public void merges(Integer profileId,List<ProfileRoleEnum> newList){
//        List<ProfileRoleEnum> oldListc = profileRoleRepository.getRoleListByProfileId(profileId);
//        newList.stream().filter(a-> !oldListc.contains(a)).forEach(f -> create(profileId, f));//create Role Plus qiladi
//        oldListc.stream().filter(ket-> !newList.contains(ket)).forEach(d-> profileRoleRepository.deleteByIdAndRoleEnum(profileId,d));
//    }

    public void create(Integer profileId, ProfileRoleEnum role) {
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRoles(role);
        profileRoleRepository.save(entity);
    }



    public void deleteRolesByProfileId(Integer profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }


    public List<ProfileRoleEnum> getByProfileId(Integer id) {
        return profileRoleRepository.getRoleListByProfileId(id);
    }
}


