package com.ahriknow.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class PageController {

    @GetMapping(value = "")
    public String home() {
        return "index.html";
    }

    @GetMapping(value = "index")
    public String index() {
        return "index.html";
    }
}
