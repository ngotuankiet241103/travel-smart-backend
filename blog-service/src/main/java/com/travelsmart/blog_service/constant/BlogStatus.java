package com.travelsmart.blog_service.constant;

import java.util.List;

public enum BlogStatus {
    ACCEPT,PENDING,REJECT,BLOCK;
    public static  List<BlogStatus> getBlogStatues(){
        return List.of(ACCEPT,REJECT,BLOCK,PENDING);
    }

}
