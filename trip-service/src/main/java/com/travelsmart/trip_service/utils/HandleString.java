package com.travelsmart.trip_service.utils;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Random;
import java.util.regex.Pattern;

public class HandleString {
    private static String removeDiacritics(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    public static String strToCode(String str) {
        str = removeDiacritics(str);
        String[] codes = str.trim().split(" ");
        StringBuffer code = new StringBuffer();
        if(codes.length < 1) {
            return codes[0].toLowerCase();
        }
        for(int i =0; i < codes.length;i++) {
            if(i == codes.length -1) {
                code.append(codes[i].toLowerCase());
            }
            else {
                code.append(codes[i].toLowerCase() + "-");
            }

        }
        return code.toString();
    }
    public static String buildSearchParam(String search){
        StringBuilder builder = new StringBuilder();
        builder.append("%");
        builder.append(search);
        builder.append("%");
        return builder.toString();
    }
    public static String createRandomCode(int codeLength){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output ;
    }
}
