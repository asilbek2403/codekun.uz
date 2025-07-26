package dasturlashasil.uz.mapperL;

import java.time.LocalDateTime;

public interface ProfileFilterMapper {
    Integer getId();
    String getName();
    String getSurname();
    String getUsername();
    String getaStus();
    LocalDateTime getCreatedDate();
    Object [] getRoles();
}

