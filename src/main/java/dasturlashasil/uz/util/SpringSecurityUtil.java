package dasturlashasil.uz.util;

import dasturlashasil.uz.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
}
