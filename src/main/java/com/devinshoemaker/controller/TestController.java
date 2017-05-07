package com.devinshoemaker.controller;

import com.devinshoemaker.dao.Test;
import com.devinshoemaker.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD operations for the test table.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
@RestController
public class TestController {

    private final TestRepository testRepository;

    @Autowired
    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    /**
     * Create a new test record.
     *
     * @param test A new test object.
     * @return The created test record.
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<Test> create(@RequestBody Test test) {
        return new ResponseEntity<>(testRepository.save(test), HttpStatus.CREATED);
    }

    /**
     * Get a test by it's ID.
     *
     * @param id The ID of the test record.
     * @return The test record that corresponds with the given ID.
     */
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public ResponseEntity<Test> findById(@PathVariable int id) {
        return new ResponseEntity<>(testRepository.findOne(id), HttpStatus.OK);
    }

    /**
     * Get all created test records.
     *
     * @return All created test records.
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<List<Test>> findAll() {
        return new ResponseEntity<>((List<Test>) testRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Update an existing test record.
     *
     * @param test A new test object.
     * @return The updated test record.
     */
    @RequestMapping(value = "/test/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Test> update(@PathVariable int id, @RequestBody Test test) {
        test.setId(id);
        return new ResponseEntity<>(testRepository.save(test), HttpStatus.OK);
    }

    /**
     * Delete a test record.
     *
     * @param id The ID of the test record to be deleted.
     * @return Response code 200.
     */
    @RequestMapping(value = "/test/{id}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable int id) {
        testRepository.delete(id);
        return HttpStatus.OK;
    }

}
