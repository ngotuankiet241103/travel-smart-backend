package com.travelsmart.profile_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomRuntimeException extends RuntimeException{
    private ErrorCode errorCode;
}
