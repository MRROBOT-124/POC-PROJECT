package com.realtime.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class used for
 * providing rest end points
 */
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/v1")
public class ApplicationController {


    /**
     * Simple API that returns
     * Hello World! response
     * @return HELLO WORLD!
     */
    @GetMapping(value = "/hello")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
