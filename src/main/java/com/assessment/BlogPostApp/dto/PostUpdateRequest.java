package com.assessment.BlogPostApp.dto;

import com.assessment.BlogPostApp.validation.EitherTitleOrContent;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EitherTitleOrContent(
        title = "title",
        content = "content"
)
public class PostUpdateRequest {

    private String title;
    private String content;
}
