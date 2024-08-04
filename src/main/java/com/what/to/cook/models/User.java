package com.what.to.cook.models;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String email, String passwordHash, String salt) {}
