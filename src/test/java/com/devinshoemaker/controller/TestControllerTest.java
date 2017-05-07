package com.devinshoemaker.controller;

import com.devinshoemaker.dao.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test TestController methods.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @org.junit.Test
    public void create() throws Exception {
        Test test = new Test();
        test.setName(UUID.randomUUID().toString());
        test.setActive(true);

        Test responseBody = createNewTest(test);
        assertTestEquals(test, responseBody);
        deleteTest(responseBody.getId());

        test.setName(UUID.randomUUID().toString());
        test.setActive(false);

        responseBody = createNewTest(test);
        assertTestEquals(test, responseBody);
        deleteTest(responseBody.getId());
    }

    @org.junit.Test
    public void findById() throws Exception {
        Gson gson = new Gson();
        Test test = new Test();
        test.setName(UUID.randomUUID().toString());
        test.setActive(true);

        Test responseBody = createNewTest(test);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/test/" + responseBody.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        responseBody = gson.fromJson(result.getResponse().getContentAsString(), Test.class);

        assertTestEquals(test, responseBody);
        deleteTest(responseBody.getId());
    }

    @org.junit.Test
    public void findAll() throws Exception {
        Gson gson = new Gson();
        Test test = new Test();
        test.setName(UUID.randomUUID().toString());
        test.setActive(true);

        int numberOfRecords = 5;

        List<Test> createdTests = new ArrayList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            test.setName(UUID.randomUUID().toString());
            createdTests.add(createNewTest(test));
        }

        List<Test> allTests;
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        allTests = gson.fromJson(result.getResponse().getContentAsString(), new TypeToken<ArrayList<Test>>(){}.getType());

        for (Test singleTest : allTests) {
            assertNotNull(singleTest);
            assertNotNull(singleTest.getId());
            assertNotNull(singleTest.getName());
        }

        for (Test singleTest : createdTests) {
            deleteTest(singleTest.getId());
        }
    }

    @org.junit.Test
    public void update() throws Exception {
        Test test = new Test();
        test.setName(UUID.randomUUID().toString());
        test.setActive(true);

        Test responseBody = createNewTest(test);

        responseBody = getTestById(responseBody.getId());
        assertTestEquals(test, responseBody);
        deleteTest(responseBody.getId());
    }

    @org.junit.Test
    public void delete() throws Exception {
        Test test = new Test();
        test.setName(UUID.randomUUID().toString());
        test.setActive(true);

        Test responseBody = createNewTest(test);

        deleteTest(responseBody.getId());
        responseBody = getTestById(responseBody.getId());
        assertNull(responseBody);
    }

    private Test createNewTest(Test test) throws Exception {
        Gson gson = new Gson();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(test))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return gson.fromJson(result.getResponse().getContentAsString(), Test.class);
    }

    private Test getTestById(int id) throws Exception {
        Gson gson = new Gson();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/test/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return gson.fromJson(result.getResponse().getContentAsString(), Test.class);
    }

    private void deleteTest(int id) throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/test/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void assertTestEquals(Test test, Test responseBody) {
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertNotNull(responseBody.getName());
        assertEquals(responseBody.getName(), test.getName());
        assertEquals(responseBody.isActive(), test.isActive());
    }

}
