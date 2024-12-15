package com.spring.springecommerceapi.exception;

public enum ErrorCode {

    SUCCESS(200, "Success"),
    CREATE_SUCCESS(201, "Create Success"),
    DELETE_SUCCESS(200, "Delete Success"),
    UPDATE_SUCCESS(200, "Update Success"),

    USER_NOT_FOUND(400, "User Not Found"),

    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    PRODUCT_ALREADY_EXISTS(409, "Product Already Exists"),
    PRODUCT_ALREADY_DELETED(409, "Product Already Deleted"),

    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    INVALID_CATEGORY(400, "Invalid Category"),
    ;
    private int code;
    private String message;

     ErrorCode(int code ,String message){
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
