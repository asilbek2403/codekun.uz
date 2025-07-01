package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.auth.AuthorizationDto;
import dasturlashasil.uz.Dto.auth.RegistrationDto;
import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.service.AuthService;
//import dasturlashasil.uz.service.AuthServiceV2bbbb;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        @Autowired
        private AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDto dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/email/verification/{token}") //generalmidi?
    public ResponseEntity<String> registration( @PathVariable("token") String token) {
        return ResponseEntity.ok(authService.regEmailVerification(token));
    }

//    @GetMapping("/registration/email/verification/{username}/{code}") //registration uchun
//    public ResponseEntity<String> registration(@PathVariable("username") String username, @PathVariable("code") int code ){
//        return ResponseEntity.ok(authService.regEmailVerification(username,code));
//    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@Valid @RequestBody AuthorizationDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }



    @PostMapping("/test-request-person-d")
    public ResponseEntity<String> test( @RequestHeader("Profile-Id") Integer id ) {
        System.out.println(id);

        return ResponseEntity.ok("VVVVVVV");
    }



    @PostMapping("/test-request-person/jwt")
    public ResponseEntity<String> test( @RequestHeader("Authorization") String jwt ) {
        System.out.println(jwt);

        return ResponseEntity.ok("VVV JWT VVV");
    }

}
