package com.devinshoemaker.controller;

import com.devinshoemaker.dao.Account;
import com.devinshoemaker.repository.AccountRepository;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;
import org.json.JSONObject;

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

    /** Return the number of accounts in the database **/
    @RequestMapping(value = "/account_count", method = RequestMethod.GET)
    public ResponseEntity<String> count_account() {
        String account_count = "Number of accounts: " + accountRepository.count();
        return new ResponseEntity<String>(account_count, HttpStatus.OK);
    }

    /**
     * Return the name of the most commonly used names and all user id with that name
     * If there are several most common names with the same frequency, return all of them with user id
     */
    @RequestMapping(value = "/common_account", method = RequestMethod.GET)
    public ResponseEntity most_common_name() {
        SetMultimap<String, Long> account_map = HashMultimap.create();
        List<Account> account_list = (List<Account>) accountRepository.findAll();
        ArrayList<String> most_freq_name_list = new ArrayList<String>();
        int most_freq_count = 0;
        if(account_list.isEmpty()){
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND);
        }
        for(Account account : account_list){
            account_map.put(account.getName(),new Long (account.getId()));
            int name_count = account_map.get(account.getName()).size();
            if(name_count>most_freq_count)
                most_freq_count = name_count;
        }
        for(String account_name : account_map.keySet()){
            int name_count = account_map.get(account_name).size();
            if(most_freq_count == name_count){
                most_freq_name_list.add(account_name);
            }
        }
        JSONObject res = new JSONObject();
        for(String account_name: most_freq_name_list){
            Set<Long> ids = account_map.get(account_name);
            res.put(account_name,ids);
        }
        return new ResponseEntity(res.toString(), HttpStatus.OK);
    }

    /**
     * Return a list of top 3 most common names by using bucket sort.
     * Time Complexity:
     * Best Case: O(n) (uniform distribution)
     * Worst Case: O(n2) (all items in a single bucket)
     */
    public List<String> top3_frequent(HashMap<String,Integer> frequencyMap) {
        List<String>[] bucket = new List[frequencyMap.size() + 1];
        List<String> res = new ArrayList<>();
        for (String key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }
        for (int pos = bucket.length - 1; pos >= 0 && res.size() < 3; pos--) {
            if (bucket[pos] != null) {
                res.addAll(bucket[pos]);
            }
        }
        while(res.size()>3){
            res.remove(res.size()-1);
        }
        return res;
    }

    /** Return the name for the top 3 most commonly used names **/
    @RequestMapping(value = "/common_account_3", method = RequestMethod.GET)
    public ResponseEntity most_common_names_top3() {
        SetMultimap<String, Long> account_map = HashMultimap.create();
        HashMap<String, Integer> name_freq_map = new HashMap<String,Integer>();
        List<Account> account_list = (List<Account>) accountRepository.findAll();
        for(Account account : account_list){
            name_freq_map.put(account.getName(),name_freq_map.getOrDefault(account.getName(), 0) + 1);
        }
        if(name_freq_map.size()<3){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        List<String> res = top3_frequent(name_freq_map);
        return new ResponseEntity(res.toString(), HttpStatus.OK);
    }

    /*public HashMap<String,Integer> sort_map(HashMap<String,Integer> freq_map){
        Stream<Map.Entry<String,Integer>> sorted =
                freq_map.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue());
        return (HashMap<String, Integer>) sorted;
    }*/
}
