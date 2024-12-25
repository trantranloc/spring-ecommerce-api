package com.spring.springecommerceapi.exception;

public enum ErrorCode {

    SUCCESS(200, "Success"),
    FAIL(400, "Fail"),
    UNAUTHENTICATED(401, "Unauthenticated"),
    CREATE_SUCCESS(201, "Create Success"),
    DELETE_SUCCESS(200, "Delete Success"),
    UPDATE_SUCCESS(200, "Update Success"),
                
    INVALID_INPUT(400, "Invalid Email or Password"),
    EMAIL_NOT_FOUND(400,"Email Not Found"),
    USER_NOT_FOUND(400, "User Not Found"),
    INVALID_USERNAME_OR_PASSWORD(401, "Invalid Username or Password"),
    INVALID_EMAIL_OR_PASSWORD(401, "Invalid Email or Password"),
    ACCOUNT_LOCKED(403, "Account Locked"),
USER_ALREADY_EXISTS(409, "USER Already Exists"),
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    PRODUCT_ALREADY_EXISTS(409, "Product Already Exists"),
    PRODUCT_ALREADY_DELETED(409, "Product Already Deleted"),

    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    INVALID_CATEGORY(400, "Invalid Category"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request"),
    RESOURCE_NOT_FOUND(404, "Resource Not Found"),
    DATABASE_ERROR(500, "Database Error"),

    UNAUTHORIZED_ACCESS(403, "Unauthorized Access"),
    UNEXPECTED_ERROR(500, "Unexpected Error");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
