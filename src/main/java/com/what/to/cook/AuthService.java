package com.what.to.cook;

import com.what.to.cook.models.Session;
import com.what.to.cook.models.User;
import com.what.to.cook.repositories.SessionRepository;
import com.what.to.cook.repositories.UserRepository;
import com.what.to.cook.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    @Autowired
    SessionRepository sessionRepository;

    Optional<AggregateReference<User, Integer>> getUserIdFromRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String sessionToken = AuthUtils.getSessionToken(request, response).orElse(null);
        if (sessionToken == null) {
            return Optional.empty();
        }

        Session session = sessionRepository.findBySessionToken(sessionToken).orElse(null);
        if (session == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("message", "Session token invalid");
            return Optional.empty();
        }

        return Optional.of(session.userId());
    }
}
