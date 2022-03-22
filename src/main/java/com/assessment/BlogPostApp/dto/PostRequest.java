package com.assessment.BlogPostApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {

    @NotNull(message = "The blog tile must not be blank!")
    private String title;

    @NotEmpty(message = "The blog content must not be blank!")
    private String content;
}
