package dasturlashasil.uz.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MapperUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime localDateTime(Object o) {
        return o == null ? null : LocalDateTime.parse(String.valueOf(o).substring(0, 19), formatter);
    }

}

