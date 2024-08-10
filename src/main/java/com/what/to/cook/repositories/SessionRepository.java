package com.what.to.cook.repositories;

import com.what.to.cook.models.Session;
import com.what.to.cook.models.User;
import java.util.Optional;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Optional<Session> findBySessionToken(String sessionToken);
    Optional<Session> findByUserId(AggregateReference<User, Integer> userId);
}
