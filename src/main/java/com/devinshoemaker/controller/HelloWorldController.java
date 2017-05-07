package com.devinshoemaker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API that returns "Hello World".
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
@RestController
public class HelloWorldController {

    /**
     * End point to return "Hello World".
     *
     * @return "Hello World" string.
     */
    @RequestMapping("/")
    public String getHelloWorld() {
        return "Hello World";
    }

}

