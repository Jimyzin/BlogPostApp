package com.assessment.BlogPostApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {

    private String title;
    private String content;
}
