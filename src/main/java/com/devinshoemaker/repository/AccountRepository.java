package com.devinshoemaker.repository;

import com.devinshoemaker.dao.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Database operations for the account table.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

}
