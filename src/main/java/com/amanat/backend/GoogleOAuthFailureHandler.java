package com.amanat.backend;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class GoogleOAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // Log the exact failure reason (very useful for debugging)
        System.err.println("OAuth2 Login FAILED: " + exception.getMessage());
        exception.printStackTrace(); // full stack trace in console

        // Store error message in session so frontend can display it
        String errorMessage = "Google login failed: " + exception.getMessage();
        request.getSession().setAttribute("loginError", errorMessage);

        // Optional: more specific messages
        if (exception.getMessage().contains("access_denied")) {
            errorMessage = "You cancelled the Google login. Please try again.";
        } else if (exception.getMessage().contains("invalid_grant") || exception.getMessage().contains("invalid_client")) {
            errorMessage = "Authentication configuration issue. Contact support.";
        }

        // Redirect back to login/landing page with error flag
        response.sendRedirect("/index.html?error=true");
        // Alternative: redirect to dedicated error page
        // response.sendRedirect("/error.html");
    }
}
