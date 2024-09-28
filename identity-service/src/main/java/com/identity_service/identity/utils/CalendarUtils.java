package com.identity_service.identity.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    public static Date plusTime(int time,int timePlus){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(time,timePlus);
        return calendar.getTime();
    }
}
