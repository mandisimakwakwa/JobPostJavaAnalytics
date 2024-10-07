package com.input.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.input.constants.AppConstants;
import com.input.services.DataInputService;


@RestController
@RequestMapping(AppConstants.REST)
public class DataInputController {

    @Autowired
    private DataInputService dataInputService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping( value = "data/inputs")
    public void runDataInputCron() {}
}
