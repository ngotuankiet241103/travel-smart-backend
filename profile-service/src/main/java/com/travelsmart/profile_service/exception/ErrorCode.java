package com.travelsmart.profile_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    FIRSTNAME_INVALID(1003, "First name must be not empty", HttpStatus.BAD_REQUEST),
    LASTNAME_INVALID(1004,"Last name must be not empty",HttpStatus.BAD_REQUEST),
    FILE_INVALID(1003, "File must be not null", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1003, "You don't have permission to access resources", HttpStatus.FORBIDDEN),
    UNAUTHORIZED(1003, "Unauthorizated user", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1003, "Wrong password", HttpStatus.BAD_REQUEST),
    UNUPLOADFILE_EXCEPTION( 1004,"Upload file failed",HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND( 1004,"Resource not found",HttpStatus.NOT_FOUND),
    TOKEN_NOT_EXISTS(1004,"Token not exists",HttpStatus.BAD_REQUEST),
    UNSUPPORTED_METHOD( 1005,"Method is not supported",HttpStatus.METHOD_NOT_ALLOWED),
    BAD_REQUEST( 1006,"Bad requested",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus status;
}
