package com.assessment.BlogPostApp.exception;

public class IncorrectUserException extends Exception {

    public static final String DEFAULT_INCORRECT_USER_EXCEPTION_MESSAGE = "Unable to extract the user information from credentials";

    public IncorrectUserException(String message) {
        super(message);
    }
}
