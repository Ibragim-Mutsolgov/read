package com.example.read.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SwaggerController {

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String swaggerHtml() {
        return "redirect:/swagger-ui/index.html";
    }
}
