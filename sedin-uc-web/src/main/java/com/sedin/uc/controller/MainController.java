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

}
