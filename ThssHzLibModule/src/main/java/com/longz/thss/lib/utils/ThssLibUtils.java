package com.longz.thss.lib.utils;

import java.time.*;

public class ThssLibUtils {

    public static String getEntityId(String profix){
        StringBuilder result = new StringBuilder();
        LocalDate now = LocalDate.now();
        result.append(profix);
        String timeSequence = (now.atTime(LocalTime.now()).toString()).replaceAll("[T.:-]","");
        result.append(timeSequence);
        return result.toString();
    }

    static public long getLongfromLocalDate(LocalDate ld){
        if(ld == null){
            ld = LocalDate.of(1900,1,1);
        }
        return ld.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
    static public LocalDate getLocalDatefromLong(long ll){
        if ((Long)ll == null){
            return LocalDate.of(1900,1,1);
        }else {
            return LocalDate.from(Instant.ofEpochMilli(ll).atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    static public long getLongFromLocalDateTime(LocalDateTime ldt){
        if (ldt == null){
            ldt =LocalDateTime.of(1900, 1, 1, 0, 5);
        }
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
    static public LocalDateTime getLocalDateTimeFromLong(long ll){
        if((Long)ll == null){
            return LocalDateTime.of(1900,1,1,0,5);
        }else {
            return LocalDateTime.from(Instant.ofEpochMilli(ll).atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }
}