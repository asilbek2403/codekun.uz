package dasturlashasil.uz.Dto.jwtdto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTDto {
        private String username;
        private String role;
        private Integer code;

        public JWTDto() {

        }
        public JWTDto(String username, String role) {
            this.username = username;
            this.role = role;
        }

}
