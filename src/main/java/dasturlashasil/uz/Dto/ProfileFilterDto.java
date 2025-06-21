package dasturlashasil.uz.Dto;

import dasturlashasil.uz.Enums.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDto {
    private String query;
    private ProfileRoleEnum role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
//    Filter (query (name/surname/username),role,created_date_from,created_date_to)
//    Response: ProfileInfoDTO
}
