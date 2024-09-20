package com.analytics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * DemoController
 */
@Controller
public class DemoController {

    @RequestMapping(value = "path", method=RequestMethod.GET)
    @ResponseBody
    public String index() {

        return "initial return";
    }
    
}