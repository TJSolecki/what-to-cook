package com.what.to.cook.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "USERS")
@Data
@AllArgsConstructor
public class User {

    @Id
    Integer id;

    String email;
    String passwordHash;
    String salt;
}
