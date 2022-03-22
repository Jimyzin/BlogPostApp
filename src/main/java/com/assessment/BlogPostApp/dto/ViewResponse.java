package com.assessment.BlogPostApp.dto;

import com.assessment.BlogPostApp.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewResponse {

    private Integer postId;
    private String title;
    private String content;
    private LocalDateTime creationTimestamp;
    private LocalDateTime lastUpdatedTimestamp;

    public static ViewResponse from(Post post) {
        return ViewResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .creationTimestamp(post.getCreationTimestamp())
                .lastUpdatedTimestamp(post.getLastUpdatedTimestamp())
                .build();
    }
}
