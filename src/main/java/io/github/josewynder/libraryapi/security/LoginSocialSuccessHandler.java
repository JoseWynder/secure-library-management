package io.github.josewynder.libraryapi.security;

import io.github.josewynder.libraryapi.model.User;
import io.github.josewynder.libraryapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DEFAULT_PASSWORD = "321";
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = token.getPrincipal();

        String email = principal.getAttribute("email");

        User user = userService.findByEmail(email);

        if(user == null) {
            user = registerUser(email);
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(generateLoginFromEmail(email));
        user.setPassword(DEFAULT_PASSWORD);
        user.setRoles(List.of("OPERATOR"));

        userService.save(user);
        return user;
    }

    private static String generateLoginFromEmail(String email) {
        int atIndex = email.indexOf("@");
        return atIndex > 20
                ? email.substring(0, 20)
                : email.substring(0, atIndex);
    }

}
