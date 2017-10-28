package com.sedin.uc.controller;

import com.sedin.dto.UserIdentity;
import com.sedin.uc.service.UserService;
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
    UserService userService;
//
//    @RequestMapping("/login")
//    @ResponseBody
//    public ActResult<Map> login(HttpServletResponse response ,@RequestBody UserIdentity user) throws Exception {
//        ActResult result = new ActResult();
//        result = userService.loginLocal(user);
//        if(!result.isSuccess()){
//            return  result;
//        }
//        UserIdentity identity = (UserIdentity) (((Map)result.getData()).get("user"));
//        String key = identity.getId() + "";
//        response.setHeader("SET-COOKIE", "token=" + identity.getToken() + ";path=/; HttpOnly");
//        return result;
//    }

//    @RequestMapping("/")
//    public String index() {
//        return "index";
//    }

//    @RequestMapping("/main")
//    public String main() {
//        return "main";
//    }
}
