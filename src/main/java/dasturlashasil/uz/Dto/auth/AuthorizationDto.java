package dasturlashasil.uz.Dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationDto {

    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;


}
