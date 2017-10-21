package com.sedin.uc.controller;

import com.sedin.dto.UserIdentity;
import com.sedin.uc.service.HelloService;
import com.sedin.uc.service.ThirdUserService;
import com.sedin.util.ActResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuhan on 2017-10-21.
 */
@RestController
public class ThirdUCController {

    @Autowired
    ThirdUserService thirdUserService;

    @Autowired
    HelloService helloService;

    @RequestMapping("/thirdLogin")
    @ResponseBody
    public ActResult login(@RequestBody UserIdentity user) throws Exception {
        return thirdUserService.login(user);
    }

    @RequestMapping("/thirdHello")
    public String index(@RequestParam String name) {
        return helloService.hello(name);
    }
}
