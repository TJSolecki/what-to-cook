package com.what.to.cook.models;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Session(@Id Integer id, String sessionToken, AggregateReference<User, Integer> userId, Date created) {
    public Session(String sessionToken, AggregateReference<User, Integer> userId) {
        this(null, sessionToken, userId, new Date());
    }
}
