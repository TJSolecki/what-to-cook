package com.what.to.cook.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;

public final class AuthUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    public static String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String generateSessionToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    public static Optional<String> getSessionToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("message", "No session cookie found");
            return Optional.empty();
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("session-token")) {
                return Optional.of(cookie.getValue());
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("message", "No session cookie found");
        return Optional.empty();
    }
}
