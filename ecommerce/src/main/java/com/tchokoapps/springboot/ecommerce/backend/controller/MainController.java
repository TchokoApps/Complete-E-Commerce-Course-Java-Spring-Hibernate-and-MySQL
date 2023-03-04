package com.tchokoapps.springboot.ecommerce.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"", "/", "backend"})
    public String viewHomePage() {
        return "backend/index";
    }
}
