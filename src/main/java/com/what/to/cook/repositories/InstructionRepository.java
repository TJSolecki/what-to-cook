package com.what.to.cook.repositories;

import com.what.to.cook.models.Instruction;
import com.what.to.cook.models.Recipe;
import java.util.List;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends CrudRepository<Instruction, Integer> {
    List<Instruction> findByRecipeId(AggregateReference<Recipe, Integer> recipeId);
}
