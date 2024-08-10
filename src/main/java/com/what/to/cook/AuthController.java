package com.what.to.cook;

import com.what.to.cook.models.Session;
import com.what.to.cook.models.User;
import com.what.to.cook.repositories.SessionRepository;
import com.what.to.cook.repositories.UserRepository;
import com.what.to.cook.utils.AuthUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        String email = authRequest.email();
        String password = authRequest.password();
        if (!AuthUtils.isValidEmail(email)) {
            response.setStatus(400);
            return new AuthResponse("Invalid email address");
        }

        if (userRepository.findByEmail(email).orElse(null) != null) {
            response.setStatus(400);
            return new AuthResponse("Email already in use");
        }

        String salt = AuthUtils.generateSalt();
        String passwordHash = AuthUtils.hashPassword(password, salt);
        User user = new User(null, email, passwordHash, salt);
        userRepository.save(user);

        String sessionToken = AuthUtils.generateSessionToken();
        sessionRepository.save(new Session(sessionToken, AggregateReference.to(user.getId())));
        response.addCookie(new Cookie("session-token", sessionToken));

        response.setStatus(HttpStatus.CREATED.value());
        return new AuthResponse("User created successfully", user.getId());
    }

    record AuthRequest(String email, String password) {}

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        String email = authRequest.email();
        String password = authRequest.password();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new AuthResponse("Invalid email or password");
        }

        String inputPasswordHash = AuthUtils.hashPassword(password, user.getSalt());
        if (!inputPasswordHash.equals(user.getPasswordHash())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new AuthResponse("Invalid email or password");
        }

        Session existingSessionForUser = sessionRepository
            .findByUserId(AggregateReference.to(user.getId()))
            .orElse(null);
        if (existingSessionForUser != null) {
            sessionRepository.delete(existingSessionForUser);
        }

        String sessionToken = AuthUtils.generateSessionToken();
        sessionRepository.save(new Session(sessionToken, AggregateReference.to(user.getId())));
        response.addCookie(new Cookie("session-token", sessionToken));
        return new AuthResponse("Logged in successfully", user.getId());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.badRequest().body("No session token found");
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("session-token")) {
                String sessionToken = cookie.getValue();
                Session session = sessionRepository.findBySessionToken(sessionToken).orElse(null);
                if (session != null) {
                    // Remove the session token cookie
                    Cookie deleteCookie = new Cookie("session-token", "");
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);

                    sessionRepository.delete(session);
                    return ResponseEntity.ok("Logged out successfully");
                }
            }
        }

        return ResponseEntity.badRequest().body("Invalid session token");
    }

    @GetMapping("/who-am-i")
    public ResponseEntity<AuthResponse> whoAmI(HttpServletRequest request, HttpServletResponse response) {
        String sessionToken = AuthUtils.getSessionToken(request, response).orElse(null);
        if (sessionToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("No session token provided."));
        }

        Session session = sessionRepository.findBySessionToken(sessionToken).orElse(null);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new AuthResponse("Session token found, but not valid.")
            );
        }

        return ResponseEntity.ok().body(new AuthResponse("Session valid.", session.userId().getId()));
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Data
    class AuthResponse {

        @NonNull
        String message;

        @Nullable
        Integer userId;
    }
}
