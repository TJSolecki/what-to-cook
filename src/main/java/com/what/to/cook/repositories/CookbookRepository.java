package com.what.to.cook.repositories;

import com.what.to.cook.models.Cookbook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookbookRepository extends CrudRepository<Cookbook, Integer> {}
