package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientsController {

    @GetMapping("/clients")
    public String index(){
        return "/index.html";
    }
}
