package com.sedin.uc.controller;

import com.github.pagehelper.PageInfo;
import com.sedin.model.MUser;
import com.sedin.uc.service.UserService;
import com.sedin.util.ActResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuhan on 2017-10-18.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    @ResponseBody
    public PageInfo<MUser> list(Integer page , Integer pageSize , MUser mUser) {
        if (page == null ) page = 1;
        if (pageSize == null ) pageSize = 10;

        return userService.getListPage(page , pageSize , mUser);
    }


    @RequestMapping("remove")
    @ResponseBody
    public ActResult remove(Long id) {
        userService.deleteByIds(String.valueOf(id));
        return ActResult.success();
    }

    @RequestMapping("edit")
    @ResponseBody
    public ActResult edit(@RequestBody MUser mUser) {
        userService.createOrUpdate(mUser);
        return ActResult.success(mUser);
    }


    @RequestMapping("add")
    @ResponseBody
    public ActResult add(@RequestBody MUser mUser) {
        userService.createOrUpdate(mUser);
        return ActResult.success(mUser);
    }
}
