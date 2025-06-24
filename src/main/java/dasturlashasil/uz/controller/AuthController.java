package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.auth.RegistrationDto;
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



}
