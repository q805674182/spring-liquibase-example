package com.devinshoemaker.controller;

import com.devinshoemaker.dao.Account;
import com.devinshoemaker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD operations for the account table.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Create a new account record.
     *
     * @param account A new account object.
     * @return The created account record.
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return new ResponseEntity<>(accountRepository.save(account), HttpStatus.CREATED);
    }

    /**
     * Get an account by it's ID.
     *
     * @param id The ID of the account record.
     * @return The account record that corresponds with the given ID.
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> findById(@PathVariable int id) {
        return new ResponseEntity<>(accountRepository.findOne(id), HttpStatus.OK);
    }

    /**
     * Get all created account records.
     *
     * @return All created account records.
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> findAll() {
        return new ResponseEntity<>((List<Account>) accountRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Update an existing account record.
     *
     * @param account A new account object.
     * @return The updated account record.
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@PathVariable int id, @RequestBody Account account) {
        account.setId(id);
        return new ResponseEntity<>(accountRepository.save(account), HttpStatus.OK);
    }

    /**
     * Delete a account record.
     *
     * @param id The ID of the account record to be deleted.
     * @return Response code 200.
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable int id) {
        accountRepository.delete(id);
        return HttpStatus.OK;
    }

}
