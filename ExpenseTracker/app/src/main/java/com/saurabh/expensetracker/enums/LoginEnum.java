package com.saurabh.expensetracker.enums;

public class LoginEnum {

    public enum SignUpErrorCodes {
        USER_NAME_EXISTS,
        PASSWORD_CONFIRM_PASSWORD_NOT_MATCH,
        WRONG_DOB_FORMAT,
        EMPTY_FIELD,
        NO_ERROR
    }

    public enum LoginErrorCodes {
        EMPTY_FIELD,
        INCORRECT_USER_NAME_OR_PASSWORD,
        NO_ERROR
    }
}
