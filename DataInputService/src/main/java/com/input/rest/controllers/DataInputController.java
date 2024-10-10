package com.input.rest.controllers;

import java.io.IOException;

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

    private DataInputService dataInputService;

    public DataInputController(DataInputService dataInputServ) {

        this.dataInputService = dataInputServ;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping( value = "data/inputs")
    public void runDataInputCron() throws IOException {

        dataInputService.createCronJobProc();
    }
}
