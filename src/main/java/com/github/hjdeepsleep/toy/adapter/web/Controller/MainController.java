package com.github.hjdeepsleep.toy.adapter.web.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public Object hello() {

        return "hello~";
    }
}