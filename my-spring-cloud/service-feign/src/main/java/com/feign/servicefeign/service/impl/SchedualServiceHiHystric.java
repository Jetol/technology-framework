package com.feign.servicefeign.service.impl;

import com.feign.servicefeign.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements HelloService {
    @Override
    public String hi(String name) {
        return "sorry "+name+" error!";
    }
}
