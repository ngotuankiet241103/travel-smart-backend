package com.identity_service.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1004,"User not found",HttpStatus.NOT_FOUND),
    SECTION_NOT_FOUND(1004,"Section not found",HttpStatus.NOT_FOUND),
    COURSE_NOT_FOUND(1004,"Course not found",HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND(1004,"Lesson not found",HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1004,"Category not found",HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1004,"Role not found",HttpStatus.NOT_FOUND),
    TOKEN_NOT_FOUND(1004,"Token not found",HttpStatus.NOT_FOUND),
    NOTE_NOT_FOUND(1004,"Note not found",HttpStatus.NOT_FOUND),
    PERSMISSION_NOT_FOUND(1004,"Permission not found",HttpStatus.NOT_FOUND),
    COUPON_NOT_FOUND(1004,"Coupon not found",HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(1004,"Comment not found",HttpStatus.NOT_FOUND),
    COUPON_EXPIRED(1004,"The number of uses for this coupon has expired",HttpStatus.NOT_FOUND),
    TITLE_INVALID(1003, "Title must be not empty", HttpStatus.BAD_REQUEST),
    SUBTITLE_INVALID(1003, "Sub title must be not empty", HttpStatus.BAD_REQUEST),
    DESCRIPTION_INVALID(1003, "Description must be not empty", HttpStatus.BAD_REQUEST),
    IMAGE_INVALID(1003, "Image must be not empty", HttpStatus.BAD_REQUEST),
    CONTENT_INVALID(1004,"Content must be not empty",HttpStatus.BAD_REQUEST),
    NAME_INVALID(1004,"Name must be not empty",HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1003, "Price must be greater than 0 or equals 0", HttpStatus.BAD_REQUEST),
    DISCOUNT_INVALID(1003, "Discount must be greater than 0 or equals 0", HttpStatus.BAD_REQUEST),
    ORDER_FAILED(1004,"You bought course",HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1004,"Token must be not empty",HttpStatus.BAD_REQUEST),
    COURSE_INVALID(1004,"Course must be not empty",HttpStatus.BAD_REQUEST),
    SECTION_INVALID(1004,"Section must be not empty",HttpStatus.BAD_REQUEST),
    LESSON_INVALID(1004,"Lesson must be not empty",HttpStatus.BAD_REQUEST),
    REVIEW_INVALID(1004,"Review must be not empty",HttpStatus.BAD_REQUEST),
    COUPON_CODE_EXISTS(1004,"Coupon code is existed",HttpStatus.BAD_REQUEST),
    RATE_MIN(1002,"Rate min is 1",HttpStatus.BAD_REQUEST),
    RATE_MAX(1002,"Rate max is 5",HttpStatus.BAD_REQUEST),
    NOTE_INVALID(1004,"Note must be not empty",HttpStatus.BAD_REQUEST),
    SECTION_SOURCE_INVALID(1004,"Section source must be not empty",HttpStatus.BAD_REQUEST),
    SECTION_DES_INVALID(1004,"Section des must be not empty",HttpStatus.BAD_REQUEST),
    POSITION_INVALID(1004,"Position must be greater than 0",HttpStatus.BAD_REQUEST),
    TOKEN_EXPRIRED(1004,"Refresh token is expired. Please login to get new token ",HttpStatus.BAD_REQUEST),
    EMAIL_EXIST(1003, "Email is exist", HttpStatus.BAD_REQUEST),
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
