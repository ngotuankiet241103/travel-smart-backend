package com.travelsmart.recommend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private int code;
    private String message;
    private T result;
}
