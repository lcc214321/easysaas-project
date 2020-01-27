package org.easymis.easysaas.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiTestController {

    @PostMapping("/demo")
    private String  demo(){
        return "demo";
    }
    @GetMapping("/access")
    private String  access(){
        return "demo";
    }
}
