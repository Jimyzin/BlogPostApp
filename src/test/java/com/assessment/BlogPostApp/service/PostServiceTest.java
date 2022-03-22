package com.assessment.BlogPostApp.service;

import com.assessment.BlogPostApp.dto.PostRequest;
import com.assessment.BlogPostApp.entity.Authority;
import com.assessment.BlogPostApp.entity.Post;
import com.assessment.BlogPostApp.entity.User;
import com.assessment.BlogPostApp.exception.BlogPostNotFoundException;
import com.assessment.BlogPostApp.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @MockBean
    DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenCreatePost_thenReturnSuccessfulPostResponse() {
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(mockPost(1));
        var postResponse = postService.create(mockUser(), mockPostRequest(1));
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getPostId()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenUpdatePost_thenReturnSuccessfulPostResponse() throws BlogPostNotFoundException {
        when(postRepository.findByPostIdAndUser(anyInt(), any(User.class))).thenReturn(Optional.of(mockPost(1)));
        when(postRepository.save(any(Post.class))).thenReturn(mockPost(1));

        var postResponse = postService.update(mockUser(), mockPostRequest(1), 1);
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getPostId()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenUpdatePostThrowsBlogPostNotFoundException_thenAssertException() {
        Assertions.assertThrows(BlogPostNotFoundException.class,
                () -> postService.update(mockUser(), mockPostRequest(1), 1)
        );

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenViewAll_thenReturnPostResponses() {
        when(postRepository.findAll()).thenReturn(List.of(mockPost(1)));

        var postResponses = postService.viewAll();
        assertThat(postResponses).isNotNull();
        assertThat(postResponses.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenView_thenReturnPostResponse() throws BlogPostNotFoundException {
        when(postRepository.findByPostId(anyInt())).thenReturn(Optional.of(mockPost(1)));

        var postResponse = postService.view(1);
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getPostId()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenViewThrowsBlogPostNotFoundException_thenAssertException() {
        Assertions.assertThrows(BlogPostNotFoundException.class,
                () -> postService.view(1)
        );

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenMyViewAll_thenReturnPostResponses() {
        when(postRepository.findAllByUser(any(User.class))).thenReturn(List.of(mockPost(1)));

        var postResponses = postService.myViewAll(mockUser());
        assertThat(postResponses).isNotNull();
        assertThat(postResponses.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenDelete_thenSuccessful() throws BlogPostNotFoundException {
        when(postRepository.findByPostIdAndUser(anyInt(), any(User.class))).thenReturn(Optional.of(mockPost(1)));
        postService.delete(mockUser(), 1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenDeleteThrowsBlogPostNotFoundException_thenAssertException() {
        Assertions.assertThrows(BlogPostNotFoundException.class,
                () -> postService.delete(mockUser(), 1)
        );
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenAdminDelete_thenSuccessful() throws BlogPostNotFoundException {
        when(postRepository.findByPostId(anyInt())).thenReturn(Optional.of(mockPost(1)));
        postService.adminDelete(1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenAdminDeleteThrowsBlogPostNotFoundException_thenAssertException() {
        Assertions.assertThrows(BlogPostNotFoundException.class,
                () -> postService.adminDelete(1)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void givenUser_whenAdminDelete_thenThrowAccessDeniedException() throws BlogPostNotFoundException {
        Assertions.assertThrows(AccessDeniedException.class,
                () -> postService.adminDelete(1)
        );
    }


    private Post mockPost(int postId) {
        var now = LocalDateTime.now();
        return Post.builder()
                .postId(postId)
                .title("Test Title - " + postId)
                .content("Test content - " + postId)
                .creationTimestamp(now)
                .lastUpdatedTimestamp(now)
                .build();
    }

    private PostRequest mockPostRequest(int postId) {
        var postRequest = new PostRequest();
        postRequest.setTitle("Test Title - " + postId);
        postRequest.setContent("Test content - " + postId);
        return postRequest;
    }

    private User mockUser() {
        return User.builder()
                .username("admin")
                .password("admin")
                .authorities(List.of(Authority.builder()
                        .authorityId(1)
                        .authority("ADMIN")
                        .build()))
                .userId(1)
                .build();
    }
}
