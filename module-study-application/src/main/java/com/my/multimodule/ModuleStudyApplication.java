package com.my.multimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.my.multimodule")
@RestController
public class ModuleStudyApplication {

    private final MyService myService;

    public ModuleStudyApplication(MyService service) {
        this.myService = service;
    }

    @GetMapping("/")
    public String home() {
        return myService.message();
    }

    public static void main(String[] args) {
        SpringApplication.run(ModuleStudyApplication.class, args);
    }

}
