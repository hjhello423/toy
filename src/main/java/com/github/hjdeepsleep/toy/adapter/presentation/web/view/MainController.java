package com.github.hjdeepsleep.toy.adapter.presentation.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @GetMapping("/hello")
    public Object hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }
}