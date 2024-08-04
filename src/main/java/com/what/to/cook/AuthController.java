package com.what.to.cook;

import com.what.to.cook.models.Session;
import com.what.to.cook.models.User;
import com.what.to.cook.repositories.SessionRepository;
import com.what.to.cook.repositories.UserRepository;
import com.what.to.cook.utils.AuthUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<String> registerUser(
        @RequestParam String email,
        @RequestParam String password,
        HttpServletResponse response
    ) {
        if (!AuthUtils.isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }

        if (userRepository.findByEmail(email).orElse(null) == null) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        String salt = AuthUtils.generateSalt();
        String passwordHash = AuthUtils.hashPassword(password, salt);
        User user = new User(null, email, passwordHash, salt);
        userRepository.save(user);

        String sessionToken = AuthUtils.generateSessionToken();
        sessionRepository.save(new Session(sessionToken, AggregateReference.to(user.getId())));
        response.addCookie(new Cookie("session-token", sessionToken));

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
        @RequestParam String email,
        @RequestParam String password,
        HttpServletResponse response
    ) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String inputPasswordHash = AuthUtils.hashPassword(password, user.getSalt());
        if (!inputPasswordHash.equals(user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String sessionToken = AuthUtils.generateSessionToken();
        response.addCookie(new Cookie("session-token", sessionToken));
        return ResponseEntity.ok("Logged in successfully");
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
}
