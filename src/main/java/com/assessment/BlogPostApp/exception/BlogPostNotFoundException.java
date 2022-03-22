package com.assessment.BlogPostApp.exception;

public class BlogPostNotFoundException extends Exception {

    public static final String DEFAULT_BLOG_POST_NOT_FOUND_EXCEPTION_MESSAGE = "Unable to find the blog post due to incorrect postId: %d";

    public BlogPostNotFoundException(String message) {
        super(message);
    }
}
