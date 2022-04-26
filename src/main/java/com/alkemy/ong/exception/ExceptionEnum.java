package com.alkemy.ong.exception;

public enum ExceptionEnum {
    ACTIVITYNOTFOUND("Activity not found"),
    CATEGORYNOTFOUND("Category not found"),
    MEMBERNOTFOUND("Member not found"),
    USERALREADYEXIST("User already exist in database"),
    USERNAMENOTFOUND("User not found"),
    ORGANIZATIONNOTFOUND("Organization not found"),
    ROLENOTFOUND("Role not found"),
    SLIDENOTFOUND("Slide not found"),
    TESTIMONIALNOTFOUND("Testimonial not found"),
    INVALIDUSERNAMEORPASSWORD("Invalid Username or Password");


    private String message;

    ExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
