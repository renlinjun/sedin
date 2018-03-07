package com.sedin.oa.controller;

import com.sedin.dto.UserIdentity;
import com.sedin.oa.third.service.UserRemoteService;
import com.sedin.util.ActResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liuhan on 2017-10-12.
 */
@Controller
public class MainController {

    @Autowired
    UserRemoteService userRemoteService;

    @RequestMapping("/login")
    @ResponseBody
    public ActResult login(HttpServletResponse response , UserIdentity user) throws Exception {
        ActResult result = new ActResult();
        result = userRemoteService.login(user);
        return result;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }
}
