package dasturlashasil.uz.service;


import dasturlashasil.uz.Dto.auth.RegistrationDto;
import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.Enums.ProfileStatusEnum;
import dasturlashasil.uz.entities.ProfileEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.repository.ProfileRepository;
import dasturlashasil.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;//serviceda rollarni create qilar edik




    @Autowired
    private EmailSenderService emailSenderService;

    public String registration ( RegistrationDto dto) {
        //validation >> DTo
        //check

        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(existOptional.isPresent()) {
            ProfileEntity profileEntity = existOptional.get();
            if (profileEntity.getProfileStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(profileEntity.getId());
                profileRepository.deleteById(profileEntity.getId());//agar activ bo'lmasa delete
            }//active bo'lsa bu
            else {
                throw new AppBadException("Username aleready exists");
            }
        }
//CREATE profile
            ProfileEntity newProfileEntity = new ProfileEntity();
            newProfileEntity.setName(dto.getName());
            newProfileEntity.setSurname(dto.getSurname());
            newProfileEntity.setUsername(dto.getUsername());
            newProfileEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            newProfileEntity.setVisible(true);
            newProfileEntity.setProfileStatus(ProfileStatusEnum.NOT_ACTIVE);

            profileRepository.save(newProfileEntity);
            //save
            //send sms

        emailSenderService.sendSimpleMessage("Registrations Sinove" , " Code SMS : 1234",
                dto.getUsername());
            //create profile roles ***
            profileRoleService.create(newProfileEntity.getId(), ProfileRoleEnum.ROLE_USER);

        return "success kodi bordi toping ";

    }




}
