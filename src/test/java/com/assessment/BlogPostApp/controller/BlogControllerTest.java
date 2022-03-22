package com.assessment.BlogPostApp.controller;

import com.assessment.BlogPostApp.dto.PostRequest;
import com.assessment.BlogPostApp.entity.Authority;
import com.assessment.BlogPostApp.entity.User;
import com.assessment.BlogPostApp.exception.BlogPostNotFoundException;
import com.assessment.BlogPostApp.exception.IncorrectUserException;
import com.assessment.BlogPostApp.service.PostService;
import com.assessment.BlogPostApp.service.UserPrincipal;
import com.assessment.BlogPostApp.uttil.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlogController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class BlogControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private SecurityUtil securityUtil;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws IncorrectUserException {
        when(securityUtil.extractUser(any(Principal.class))).thenReturn(mockUser());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenViewAll_thenReturnHttpOK() throws Exception {
        this.mockMvc.perform(get("/blog/viewAll")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenMyViewAll_thenReturnHttpOK() throws Exception {
        this.mockMvc.perform(get("/blog/myViewAll")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenView_thenReturnHttpOK() throws Exception {
        this.mockMvc.perform(get("/blog/1/view")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenCreate_thenReturnHttpCreated() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/blog/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPostRequest()))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenUpdate_thenReturnHttpOK() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(patch("/blog/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPostRequest()))
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenUpdateThrowsBlogPostNotFoundException_thenReturnHttpNotFound() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        when(postService.update(any(User.class), any(PostRequest.class), any(Integer.class)))
                .thenThrow(BlogPostNotFoundException.class);
        this.mockMvc.perform(patch("/blog/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPostRequest()))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"ADMIN"})
    public void givenAdmin_whenDelete_thenReturnHttpNoContent() throws Exception {
        this.mockMvc.perform(delete("/blog/1/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void givenAdmin_whenAdminDelete_thenReturnHttpNoContent() throws Exception {
        this.mockMvc.perform(delete("/blog/1/adminDelete"))
                .andExpect(status().isNoContent());
    }

    private PostRequest mockPostRequest() {
        var postRequest = new PostRequest();
        postRequest.setTitle("Test Blog Post");
        postRequest.setContent("Test content in MockMvc");

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
