package dasturlashasil.uz.util;

import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

//
//
//public class SpringSecurityUtil {
//
//
//    public static Integer currentProfileId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
//        return user.getId();
//    }
//
//
//
//}
public class SpringSecurityUtil {

    public static Integer currentProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // yoki throw new AppBadException("Not authenticated")
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails user) {
            return user.getId();
        }

        return null; // yoki throw new AppBadException("Principal type unknown")
    }


    //Comment Service

    public static Boolean hasAnyRoles(ProfileRoleEnum... roles) {
        List<String> roleList = Arrays.stream(roles).map(Enum::name).toList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (roleList.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }




}
