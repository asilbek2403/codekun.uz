package dasturlashasil.uz.Dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlashasil.uz.Dto.AttachDto;
import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.Enums.ProfileStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private Integer id;

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;

    @NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    private String username;

    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    @NotEmpty(message = "Role bo‘sh bo‘lmasligi kerak")
    private List<ProfileRoleEnum> roleList;

    private LocalDateTime createdDate;
    //filterda kerak

    private ProfileStatusEnum status;
    private String jwt;
    private AttachDto photoId;

    private String jwtC;
//    private AttachDTO photo;
}
