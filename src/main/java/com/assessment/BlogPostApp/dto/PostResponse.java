package com.assessment.BlogPostApp.dto;

import com.assessment.BlogPostApp.entity.Post;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponse {

    private Integer postId;
    private String title;
    private String content;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

}
