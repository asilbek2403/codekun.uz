package dasturlashasil.uz.config;


import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.entities.ProfileEntity;
import dasturlashasil.uz.repository.ProfileRepository;
import dasturlashasil.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //  "username": "asilustoz37@gmail.com",
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(username + "< User >not found");
        }
        ProfileEntity profile = optional.get();//Buni ustozim korib tekshirdi Yani BAsic bilan HasRole
        List<ProfileRoleEnum> roleEntities = profileRoleRepository.getRoleListByProfileId(profile.getId());

        return new CustomUserDetails(profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getProfileStatus(),
                roleEntities);

    }

}
