package com.what.to.cook.repositories;

import com.what.to.cook.models.Session;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Optional<Session> findBySessionToken(String sessionToken);
}
