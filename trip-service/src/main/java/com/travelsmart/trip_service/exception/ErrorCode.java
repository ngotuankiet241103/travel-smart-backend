package com.travelsmart.trip_service.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    TRIP_INVALID(1003,"Trip must be not null",HttpStatus.BAD_REQUEST),
    TITLE_INVALID(1003,"Title must be not empty",HttpStatus.BAD_REQUEST),
    START_DATE_INVALID(1003,"Start date must be not null",HttpStatus.BAD_REQUEST),
    END_DATE_INVALID(1003,"End date must be not null",HttpStatus.BAD_REQUEST),
    LOCATION_INVALID(1003,"Location msut be not null",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003,"Email must be not empty",HttpStatus.BAD_REQUEST),
    PERMISSION_INVALID(1003,"Permission must be not null",HttpStatus.BAD_REQUEST),
    DESTINATION_FROM_INVALID(1003,"Destination from must be not null",HttpStatus.BAD_REQUEST),
    ITINERARY_INVALID(1003,"Itinerary must be not null",HttpStatus.BAD_REQUEST),
    ITINERARY_FROM_INVALID(1003,"Itinerary must be not null",HttpStatus.BAD_REQUEST),
    TYPES_INVALID(1003,"Trip not found",HttpStatus.BAD_REQUEST),
    TRIP_NOT_FOUND(1004,"Trip not found",HttpStatus.NOT_FOUND),
    ITINERARY_NOT_FOUND(1004,"Itinerary not found",HttpStatus.NOT_FOUND),
    DESTINATION_NOT_FOUND(1004,"Destination not found",HttpStatus.NOT_FOUND),
    DESTINATION_INVALID(1004,"Destination not valid",HttpStatus.BAD_REQUEST),
    TRIP_DENIED(1004,"This trip not belong to user",HttpStatus.BAD_REQUEST),
    TRIP_WAS_SHARED(1004,"User was shared",HttpStatus.BAD_REQUEST),
    IMAGE_NOT_FOUND(1004,"Image of location not exists",HttpStatus.NOT_FOUND),
    FILE_INVALID(1003, "File must be not null", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1003, "You don't have permission to access resources", HttpStatus.FORBIDDEN),
    UNAUTHORIZED(1003, "Unauthorized user", HttpStatus.UNAUTHORIZED),
    ORDER_NOT_EXIST(1003, "You must be order this course before review this", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
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

