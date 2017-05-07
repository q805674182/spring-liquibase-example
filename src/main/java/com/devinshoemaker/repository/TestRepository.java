package com.devinshoemaker.repository;

import com.devinshoemaker.dao.Test;
import org.springframework.data.repository.CrudRepository;

/**
 * Database operations for the test table on the test schema.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
public interface TestRepository extends CrudRepository<Test, Integer> {

}
