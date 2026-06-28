package com.amanat.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class GoogleOAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public GoogleOAuthSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");
        String picture = oAuth2User.getAttribute("picture");

        //  Check existing user
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;

        if (optionalUser.isPresent()) {
            // Existing user update
            user = optionalUser.get();
            user.setName(name);
            user.setGoogleId(googleId);
            user.setProvider("GOOGLE");
            user.setProfilePic(picture);

        } else {
            // New user create
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setGoogleId(googleId);
            user.setProvider("GOOGLE");
            user.setProfilePic(picture);
            user.setCreatedAt(LocalDateTime.now());
        }

        userRepository.save(user);

        //  IMPORTANT: Session mein same attributes  AuthController use karta hai
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user.getEmail());
        session.setAttribute("userName", user.getName());
        session.setAttribute("user", user);

        // Redirect to dashboard
        response.sendRedirect("/dashboard.html");
    }
}
