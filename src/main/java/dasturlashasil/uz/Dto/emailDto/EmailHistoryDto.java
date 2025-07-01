package dasturlashasil.uz.Dto.emailDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryDto {
        private String id;
        private String email;
        private String body;

        private LocalDateTime createdDate;
}



