package com.feign.servicefeign.service;

import com.feign.servicefeign.service.impl.SchedualServiceHiHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hello" ,fallback = SchedualServiceHiHystric.class)
public interface HelloService {
    @RequestMapping(value = "hi",method = RequestMethod.GET)
    String hi(@RequestParam(value = "name") String name);
}
