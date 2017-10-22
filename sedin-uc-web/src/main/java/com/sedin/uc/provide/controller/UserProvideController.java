package com.sedin.uc.provide.controller;

import com.sedin.dto.UserIdentity;
import com.sedin.uc.provide.service.UserProvideService;
import com.sedin.uc.service.UserService;
import com.sedin.util.ActResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuhan on 2017-10-21.
 */
@RestController
public class UserProvideController {

    @Autowired
    UserProvideService userProvideService;

    @RequestMapping("/thirdLogin")
    @ResponseBody
    public ActResult login(@RequestBody UserIdentity user) throws Exception {
        return userProvideService.login(user);
    }
    @RequestMapping("/thirdTicket")
    public String ticket(String ticket) throws Exception {
        return userProvideService.getUserByTicket(ticket);
    }
}
