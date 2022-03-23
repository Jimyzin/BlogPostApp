package com.assessment.BlogPostApp.security;

import com.assessment.BlogPostApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlogPostUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BlogPostUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        }

        throw new UsernameNotFoundException(username);
    }
}
