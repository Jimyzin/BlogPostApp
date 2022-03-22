package com.assessment.BlogPostApp.controller;

import com.assessment.BlogPostApp.dto.PostRequest;
import com.assessment.BlogPostApp.dto.PostResponse;
import com.assessment.BlogPostApp.dto.PostUpdateRequest;
import com.assessment.BlogPostApp.dto.ViewResponse;
import com.assessment.BlogPostApp.exception.BlogPostNotFoundException;
import com.assessment.BlogPostApp.exception.IncorrectUserException;
import com.assessment.BlogPostApp.service.PostService;
import com.assessment.BlogPostApp.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    private final PostService postService;
    private final SecurityUtil securityUtil;

    @Autowired
    public BlogController(PostService postService, SecurityUtil securityUtil) {
        this.postService = postService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(Principal principal, @Valid @RequestBody PostRequest postRequest) throws IncorrectUserException {
        log.info("{} is creating a blog post titled {}", principal.getName(), postRequest.getTitle());
        return postService.create(securityUtil.extractUser(principal), postRequest);
    }

    @PatchMapping("/{postId}/update")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse update(Principal principal, @Valid @RequestBody PostUpdateRequest postUpdateRequest, @PathVariable Integer postId) throws BlogPostNotFoundException, IncorrectUserException {
        log.info("{} is updating blog post id: {}", principal.getName(), postId);
        return postService.update(securityUtil.extractUser(principal), postUpdateRequest, postId);
    }

    @GetMapping("/viewAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewResponse> viewAll(Principal principal) throws IncorrectUserException {
        log.info("{} is viewing all blogs", principal.getName());
        return postService.viewAll();
    }

    @GetMapping("/{postId}/view")
    @ResponseStatus(HttpStatus.OK)
    public ViewResponse view(Principal principal, @PathVariable Integer postId) throws BlogPostNotFoundException, IncorrectUserException {
        log.info("{} is viewing blog post id: {}", principal.getName(), postId);
        return postService.view(postId);
    }

    @GetMapping("/myViewAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewResponse> myViewAll(Principal principal) throws IncorrectUserException {
        log.info("{} is viewing all self-owned blog posts ", principal.getName());
        return postService.myViewAll(securityUtil.extractUser(principal));
    }

    @DeleteMapping("/{postId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Principal principal, @PathVariable Integer postId) throws BlogPostNotFoundException, IncorrectUserException {
        log.info("{} is deleting blog post id: {}", principal.getName(), postId);
        postService.delete(securityUtil.extractUser(principal), postId);
    }

    @DeleteMapping("/{postId}/adminDelete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminDelete(Principal principal, @PathVariable Integer postId) throws BlogPostNotFoundException, IncorrectUserException {
        log.info("{} is admin-deleting blog post id: {}", principal.getName(), postId);
        postService.adminDelete(postId);
    }

}
