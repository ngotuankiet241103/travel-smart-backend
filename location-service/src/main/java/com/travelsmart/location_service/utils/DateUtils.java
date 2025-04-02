package com.travelsmart.location_service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Calendar setTime(Calendar cal, int hour, int minute, int second){
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);
        return cal;
    }
    public static LocalDateTime setStartTimeDay(LocalDate date){
        return date.atTime(LocalTime.MIN);
    }
    public static LocalDateTime setEndTimeDay(LocalDate date){
        return date.atTime(LocalTime.MAX);
    }
    public static Date convertDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }



}
