package ua.everybuy.buisnesslogic.service.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class DateService {

    public static LocalDateTime getDate(LocalDateTime date){
        return date.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

}

