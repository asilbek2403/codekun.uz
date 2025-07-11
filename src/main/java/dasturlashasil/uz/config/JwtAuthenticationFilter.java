//package dasturlashasil.uz.config;
//
//
//import dasturlashasil.uz.Dto.jwtdto.JWTDto;
//import dasturlashasil.uz.util.JWTUtil;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.util.AntPathMatcher;
//import java.io.IOException;
//import java.util.Arrays;
//
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//        return Arrays
//                .stream(SpringSecurityConfig.openApiList)
//                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
//    }
//
//
////    public static String[] openApiList = {"/attach/**"};
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
////        AntPathMatcher pathMatcher = new AntPathMatcher();
////        return Arrays
////                .stream(openApiList)
////                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
////    }
//
//
//
//    @Override// Bu filtrlardan o'tishini qaraydi agar PERMITALL() bo'lmasa tekshiraveradi, permitAllda o'tkazib yuboradi.
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response); // Continue the filter chain
//            return;
//        }
//
//        try {
//            final String token = header.substring(7).trim();
//            JWTDto jwtDTO = JWTUtil.decode(token);
//
//            String username = jwtDTO.getUsername();
//            CustomUserDetails  userDetails =(CustomUserDetails ) customUserDetailsService.loadUserByUsername(username);//USerDetails ni >>>>>> CustomUserDetails ga o'tkazib qo'ydim
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
//                    null, userDetails.getAuthorities());// Securityga uzatib yuboradi
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//            filterChain.doFilter(request, response); // Continue the filter chain
//        } catch (JwtException | UsernameNotFoundException e) {
//            filterChain.doFilter(request, response); // Continue the filter chain
//            return;
//        }
//    }
//
//}
//
package dasturlashasil.uz.config;

import dasturlashasil.uz.Dto.jwtdto.JWTDto;
import dasturlashasil.uz.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SpringSecurityConfig.openApiList)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7).trim();

            // ✅ 1. Tokenni decode qilamiz
            JWTDto jwtDTO = JWTUtil.decode(token);
            String username = jwtDTO.getUsername();

            // ✅ 2. UserDetails yuklaymiz (CustomUserDetails)
            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(username);

            // ✅ 3. SecurityContext-ga yuboramiz
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, // PRINCIPAL — String emas!
                            null,
                            userDetails.getAuthorities()
                    );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtException | UsernameNotFoundException e) {
            // Optional: log qilishingiz mumkin
        }

        filterChain.doFilter(request, response);
    }
}
