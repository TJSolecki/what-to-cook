package com.what.to.cook.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@AllArgsConstructor
@Data
public class Cookbook {

    @Id
    Integer id;

    String name;
    AggregateReference<User, Integer> ownerId;
    Integer recipeCount;
}
