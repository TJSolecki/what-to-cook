package com.what.to.cook;

import com.what.to.cook.models.User;
import com.what.to.cook.repositories.UserRepository;
import com.what.to.cook.utils.AuthUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
        @RequestParam String email,
        @RequestParam String password,
        HttpServletResponse response
    ) {
        if (!AuthUtils.isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }

        String salt = AuthUtils.generateSalt();
        String passwordHash = AuthUtils.hashPassword(password, salt);
        User user = new User(null, email, passwordHash, salt);
        userRepository.save(user);

        String sessionToken = AuthUtils.generateSessionToken();
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

        String inputPasswordHash = AuthUtils.hashPassword(password, user.salt());
        if (!inputPasswordHash.equals(user.passwordHash())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String sessionToken = AuthUtils.generateSessionToken();
        response.addCookie(new Cookie("session-token", sessionToken));
        return ResponseEntity.ok("Logged in successfully");
    }
}
