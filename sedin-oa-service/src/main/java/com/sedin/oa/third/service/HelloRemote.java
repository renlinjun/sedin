package com.sedin.oa.third.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liuhan on 2017-10-21.
 */
@FeignClient(name= "uc")
public interface HelloRemote {
    @RequestMapping(value = "/thirdHello")
    public String hello(@RequestParam(value = "name") String name);
}
