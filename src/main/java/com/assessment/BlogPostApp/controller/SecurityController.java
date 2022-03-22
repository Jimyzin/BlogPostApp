package com.assessment.BlogPostApp.controller;

import com.assessment.BlogPostApp.entity.Authority;
import com.assessment.BlogPostApp.entity.User;
import com.assessment.BlogPostApp.repository.UserAuthorityRepository;
import com.assessment.BlogPostApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final UserAuthorityRepository userAuthorityRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecurityController(UserAuthorityRepository userAuthorityRepository, UserRepository userRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    @Transactional
    public User login() {
        var user = User.builder()
                .userId(234)
                .username("user1")
                .password("pass1")
                //.userAuthorities(List.of(userAuthority))
                .build();

        var userAuthority = Authority.builder()
                .authorityId(1)
                .authority("USER")
                .users(List.of(user))
                .build();

        user.setAuthorities(List.of(userAuthority));

        userAuthorityRepository.save(userAuthority);
        userRepository.save(user);


        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
