package com.sedin.uc.service.impl;

import com.sedin.uc.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhan on 2017-10-21.
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String who) {
        return "hello ，this is first messge";
    }
}
