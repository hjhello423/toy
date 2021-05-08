package com.github.toy.adapter.presentation.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
