package com.what.to.cook.middleware;

import com.what.to.cook.models.Session;
import com.what.to.cook.repositories.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionMiddleware {

    @Autowired
    private SessionRepository sessionRepository;

    public boolean isValidSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("session-token")) {
                String sessionToken = cookie.getValue();
                Session session = sessionRepository.findBySessionToken(sessionToken).orElse(null);
                if (session != null) {
                    // Set the user ID in the request attribute
                    request.setAttribute("userId", session.userId().getId());
                    return true;
                }
            }
        }

        return false;
    }
}
