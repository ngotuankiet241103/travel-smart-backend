package com.travelsmart.location_service.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    LOCATION_NOT_FOUND(1004,"Location not found",HttpStatus.NOT_FOUND),
    INTRODUCE_NOT_FOUND(1004,"Introduce not found",HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND(1004,"Image of location not exists",HttpStatus.NOT_FOUND),
    FILE_INVALID(1003, "File must be not null", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1003, "You don't have permission to access resources", HttpStatus.FORBIDDEN),
    UNAUTHORIZED(1003, "Unauthorized user", HttpStatus.UNAUTHORIZED),
    ORDER_NOT_EXIST(1003, "You must be order this course before review this", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
    LON_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
    CATEGORY_INVALID(1003, "Category must be not empty", HttpStatus.BAD_REQUEST),
    TYPE_INVALID(1003, "Type must be not empty", HttpStatus.BAD_REQUEST),
    LOCATION_INVALID(1004,"Location must be not null",HttpStatus.BAD_REQUEST),
    CONTENT_INVALID(1004,"Content must be not empty",HttpStatus.BAD_REQUEST),
    TITLE_INVALID(1004,"Title must be not empty",HttpStatus.BAD_REQUEST),
    BOUNDINGBOX_INVALID(1003, "Bounding box must be not null", HttpStatus.BAD_REQUEST),
    IMAGE_INVALID(1003, "Image must be not null", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID(1003, "Address must be not null", HttpStatus.BAD_REQUEST),
    LAT_INVALID(1003, "Lon must be not empty", HttpStatus.BAD_REQUEST),
    NAME_INVALID(1003, "Name must be not empty", HttpStatus.BAD_REQUEST),
    STATUS_INVALID(1003, "Status must be not null", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1003, "Wrong password", HttpStatus.BAD_REQUEST),
    UNUPLOADFILE_EXCEPTION( 1004,"Upload file failed",HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND( 1004,"Resource not found",HttpStatus.NOT_FOUND),
    TOKEN_NOT_EXISTS(1004,"Token not exists",HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(1004,"Token is expired",HttpStatus.BAD_REQUEST),
    EMAIL_IS_VERIFIED(1004,"Email is verified",HttpStatus.BAD_REQUEST),
    CONFIRMTOKEN_NOT_FOUND( 1004,"Confirm token not found",HttpStatus.NOT_FOUND),
    CONFIRMTOKEN_IS_EXPIRED( 1004,"Confirn token is expired",HttpStatus.BAD_REQUEST),
    UNSUPPORTED_METHOD( 1005,"Method is not supported",HttpStatus.METHOD_NOT_ALLOWED),
    BAD_REQUEST( 1006,"Bad requested",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus status;
}

