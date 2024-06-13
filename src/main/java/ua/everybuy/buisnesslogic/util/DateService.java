package ua.everybuy.buisnesslogic.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class DateService {
//    private final static DateTimeFormatter TIME_FORMATTER =
//            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static LocalDateTime getDate(LocalDateTime date){
        return date.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

}

