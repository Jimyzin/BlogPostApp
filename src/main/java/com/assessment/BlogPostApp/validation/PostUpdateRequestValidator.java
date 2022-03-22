package com.assessment.BlogPostApp.validation;

import com.assessment.BlogPostApp.dto.PostUpdateRequest;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostUpdateRequestValidator implements ConstraintValidator<EitherTitleOrContent, PostUpdateRequest> {

    private String title;
    private String content;

    public void initialize(EitherTitleOrContent constraintAnnotation) {
        this.title = constraintAnnotation.title();
        this.content = constraintAnnotation.content();
    }

    @Override
    public boolean isValid(PostUpdateRequest postUpdateRequest, ConstraintValidatorContext constraintValidatorContext) {

        var isTitlePresent = !((postUpdateRequest.getTitle() == null) || (postUpdateRequest.getTitle().isBlank()));
        var isContentPresent = !((postUpdateRequest.getContent() == null) || (postUpdateRequest.getContent().isBlank()));

        if (!isTitlePresent && !isContentPresent) {
            return false;
        }
        return true;
    }
}
