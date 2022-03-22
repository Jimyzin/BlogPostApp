package com.assessment.BlogPostApp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PostUpdateRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EitherTitleOrContent {

    String message() default "Either title or content must be present";
    String title();
    String content();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
