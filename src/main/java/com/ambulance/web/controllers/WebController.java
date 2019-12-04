package com.ambulance.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/cards")
    public String cards() {
        return "cards";
    }

}


