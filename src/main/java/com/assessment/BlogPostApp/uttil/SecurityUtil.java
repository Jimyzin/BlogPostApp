package com.assessment.BlogPostApp.uttil;

import com.assessment.BlogPostApp.entity.User;
import com.assessment.BlogPostApp.exception.IncorrectUserException;
import com.assessment.BlogPostApp.service.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@Slf4j
public class SecurityUtil {

    public User extractUser(Principal principal) throws IncorrectUserException {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            var userPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            if (userPrincipal instanceof UserPrincipal) {
                return ((UserPrincipal) userPrincipal).getUser();
            }

        }

        log.error(IncorrectUserException.DEFAULT_INCORRECT_USER_EXCEPTION_MESSAGE);
        throw new IncorrectUserException(IncorrectUserException.DEFAULT_INCORRECT_USER_EXCEPTION_MESSAGE);
    }
}
