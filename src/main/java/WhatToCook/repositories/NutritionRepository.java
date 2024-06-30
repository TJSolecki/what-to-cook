package WhatToCook.repositories;

import WhatToCook.models.Nutrition;
import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<Nutrition, Integer> {}
