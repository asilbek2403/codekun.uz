package dasturlashasil.uz.service;


import dasturlashasil.uz.Dto.auth.AuthorizationDto;
import dasturlashasil.uz.Dto.auth.RegistrationDto;
import dasturlashasil.uz.Dto.jwtdto.JWTDto;
import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.Enums.ProfileStatusEnum;
import dasturlashasil.uz.entities.ProfileEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.repository.ProfileRepository;
import dasturlashasil.uz.service.email.EmailHistoryService;
import dasturlashasil.uz.sms.SmsSendService;
import dasturlashasil.uz.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
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

    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsSendService smsSendService;


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

//        emailSenderService.sendSimpleMessage("Registrations Sinove" , " Code SMS : 1234",
//                dto.getUsername());


        //create profile roles ***
            profileRoleService.create(newProfileEntity.getId(), ProfileRoleEnum.ROLE_USER);//send

        emailSenderService.sendRegistrationStyledEmail(newProfileEntity.getUsername());// user emailini beraib yubor
        smsSendService.sendRegistrationSms(newProfileEntity.getUsername());//sms
        return "success kodi bordi toping ";//respons

    }

//code accountda bormi
public String regEmailVerification(String token) {
    JWTDto jwtDTO = null;
    try {
        jwtDTO = JWTUtil.decodeRegistrationToken(token);
    } catch (ExpiredJwtException e) {
        throw new AppBadException("JWT Expired");
    } catch (JwtException e) {
        throw new AppBadException("JWT Not Valid");
    }


    String username = jwtDTO.getUsername();

        Optional<ProfileEntity> verProfile = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if(verProfile.isEmpty()) {
            throw new AppBadException("Username not Found");}

        ProfileEntity profile = verProfile.get();
        if(!profile.getProfileStatus().equals(ProfileStatusEnum.NOT_ACTIVE)){
            throw new AppBadException("Username int wrong status");}

//
        boolean emailYed = emailHistoryService.isSmsSendToAccount(username,jwtDTO.getCode());
//check sms code to email
        if(emailYed){
           profile.setProfileStatus(ProfileStatusEnum.ACTIVE);
           profileRepository.save(profile);
           return "Verification successfully completed OK!";
        }
        throw new AppBadException("Not Completed");

    }

    public ProfileDto login( AuthorizationDto dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(profileOptional.isEmpty()) {
            throw new AppBadException("Username or password wrong");
        }
        ProfileEntity profile = profileOptional.get();
        if( !bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("username or password Wrong");
        }

        if( !profile.getProfileStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("Username in wrong  status");
        }
        ProfileDto responseProfileDto = new ProfileDto();
        responseProfileDto.setName(profile.getName());
        responseProfileDto.setSurname(profile.getSurname());
        responseProfileDto.setUsername(profile.getUsername());
//        responseProfileDto.setPassword((dto.getPassword()));
        responseProfileDto.setStatus(profile.getProfileStatus());
        responseProfileDto.setRoleList(profileRoleService.getByProfileId(profile.getId()));
//        profileRoleService.getByProfileId(responseProfileDto.getId());
        responseProfileDto.setJwt(JWTUtil.encode(profile.getUsername(),responseProfileDto.getRoleList()));
        return responseProfileDto;
    }




}
