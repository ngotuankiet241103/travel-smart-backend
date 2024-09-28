package com.travelsmart.trip_service.utils;

import java.util.Calendar;

public class DateUtils {
    public static Calendar setTime(Calendar cal, int hour, int minute, int second){
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);
        return cal;
    }
}
