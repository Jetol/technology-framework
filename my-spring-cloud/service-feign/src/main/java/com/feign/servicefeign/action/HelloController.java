package com.feign.servicefeign.action;

import com.feign.servicefeign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping(value = "hi")
    public String hi(String name){
        String s = helloService.hi( name);
        return s ;
    }
}
