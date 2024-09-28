package com.travelsmart.location_service.utils;
public class StringUtils{
    public static String buildParamSearch(String search){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%");
        stringBuilder.append(search);
        stringBuilder.append("%");
        return stringBuilder.toString();
    }
}
