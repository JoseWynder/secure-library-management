package io.github.josewynder.libraryapi.security;

import io.github.josewynder.libraryapi.model.User;
import io.github.josewynder.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String enteredPassword = authentication.getCredentials().toString();

        User userFound = userService.findByLogin(login);

        if(userFound == null){
            throw getUserNotFoundException();
        }

        String encodedPassword = userFound.getPassword();

        boolean passwordMatches = passwordEncoder.matches(enteredPassword, encodedPassword);

        if(passwordMatches) {
            return new CustomAuthentication(userFound);
        }

        throw getUserNotFoundException();
    }

    private UsernameNotFoundException getUserNotFoundException() {
        return new UsernameNotFoundException("Incorrect username and/or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
