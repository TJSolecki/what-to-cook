package com.what.to.cook.repositories;

import com.what.to.cook.models.Nutrition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionRepository extends CrudRepository<Nutrition, Integer> {}
