package com.assessment.BlogPostApp.service;

import com.assessment.BlogPostApp.dto.PostRequest;
import com.assessment.BlogPostApp.dto.PostResponse;
import com.assessment.BlogPostApp.dto.ViewResponse;
import com.assessment.BlogPostApp.entity.Post;
import com.assessment.BlogPostApp.entity.User;
import com.assessment.BlogPostApp.exception.BlogPostNotFoundException;
import com.assessment.BlogPostApp.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Creates a new blog post.
     *
     * @param user
     * @param postRequest
     * @return created PostResponse
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PostResponse create(User user, PostRequest postRequest) {
        var now = LocalDateTime.now();
        var post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .creationTimestamp(now)
                .lastUpdatedTimestamp(now)
                .user(user)
                .build();
        var savedPost = postRepository.saveAndFlush(post);
        return PostResponse.from(savedPost);
    }

    /**
     * Updates an existing blog post. Strictly, only the owner of the post can update.
     *
     * @param user
     * @param postRequest
     * @param postId
     * @return an updated PostResponse
     * @throws BlogPostNotFoundException, if the postId does not belong to the USER
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PostResponse update(User user, PostRequest postRequest, Integer postId) throws BlogPostNotFoundException {
        // finding a post by the USER and postId
        var postOptional = postRepository.findByPostIdAndUser(postId, user);

        if (postOptional.isPresent()) {
            var post = postOptional.get();
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setLastUpdatedTimestamp(LocalDateTime.now());

            return PostResponse.from(postRepository.save(post));
        }

        var errorMessage = String.format(BlogPostNotFoundException.DEFAULT_BLOG_POST_NOT_FOUND_EXCEPTION_MESSAGE, postId);
        log.error(errorMessage);
        throw new BlogPostNotFoundException(errorMessage);
    }

    /**
     * Shows all blog posts from all users.
     *
     * @return a list of ViewResponse
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ViewResponse> viewAll() {
        return postRepository.findAll()
                .stream()
                .map(post -> ViewResponse.from(post))
                .collect(Collectors.toList());

    }

    /**
     * Shows a blog post specified by a postId. Any user can view any specific blog post even if it is owned by others.
     *
     * @param postId
     * @return
     * @throws BlogPostNotFoundException if the postId does not exist.
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ViewResponse view(Integer postId) throws BlogPostNotFoundException {
        var view = postRepository.findByPostId(postId);

        if (view.isPresent()) {
            return ViewResponse.from(view.get());
        }

        var errorMessage = String.format(BlogPostNotFoundException.DEFAULT_BLOG_POST_NOT_FOUND_EXCEPTION_MESSAGE, postId);
        log.error(errorMessage);
        throw new BlogPostNotFoundException(errorMessage);
    }

    /**
     * Shows all blog posts owned by the user.
     *
     * @param user
     * @return a list of ViewResponse
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ViewResponse> myViewAll(User user) {
        return postRepository.findAllByUser(user)
                .stream()
                .map(post -> ViewResponse.from(post))
                .collect(Collectors.toList());
    }

    /**
     * Deletes an existing blog post. Strictly, only the owner of the post can delete.
     *
     * @param user
     * @param postId
     * @throws BlogPostNotFoundException if the blog post does not belong to the user or does not exist
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(User user, Integer postId) throws BlogPostNotFoundException {
        var postOptional = postRepository.findByPostIdAndUser(postId, user);

        if (postOptional.isPresent()) {
            postRepository.deleteById(postId);
        } else {
            var errorMessage = String.format(BlogPostNotFoundException.DEFAULT_BLOG_POST_NOT_FOUND_EXCEPTION_MESSAGE, postId);
            log.error(errorMessage);
            throw new BlogPostNotFoundException(errorMessage);
        }
    }

    /**
     * Strictly, only the ADMINN can delete any blog post
     *
     * @param postId
     * @throws BlogPostNotFoundException if the postId does not exist
     */
    @PreAuthorize("hasRole('ADMIN')")
    public void adminDelete(Integer postId) throws BlogPostNotFoundException {
        var postOptional = postRepository.findByPostId(postId);

        if (postOptional.isPresent()) {
            postRepository.deleteById(postId);
        } else {
            var errorMessage = String.format(BlogPostNotFoundException.DEFAULT_BLOG_POST_NOT_FOUND_EXCEPTION_MESSAGE, postId);
            log.error(errorMessage);
            throw new BlogPostNotFoundException(errorMessage);
        }
    }


}
