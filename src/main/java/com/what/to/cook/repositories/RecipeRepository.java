package com.what.to.cook.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<com.what.to.cook.models.Recipe, Integer> {
}
