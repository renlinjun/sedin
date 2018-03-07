package com.sedin.uc.controller;

import com.github.pagehelper.PageInfo;
import com.sedin.model.MRes;
import com.sedin.model.MUser;
import com.sedin.type.MResType;
import com.sedin.uc.service.RelResService;
import com.sedin.uc.service.ResService;
import com.sedin.uc.service.UserService;
import com.sedin.util.ActResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuhan on 2017-11-03.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    ResService resService;

    @Autowired
    RelResService relResService;

    @RequestMapping("list")
    @ResponseBody
    public PageInfo<MRes> list(Integer page , Integer pageSize , MRes res) {
        if (page == null ) page = 1;
        if (pageSize == null ) pageSize = 10;

        res.setType( MResType.role.getType());
        return resService.getListPage(page , pageSize , res);
    }



    @RequestMapping("remove")
    @ResponseBody
    public ActResult remove(Long id) {
        if (id == null || id == 0 ) return ActResult.fail("参数为空！");
        if (relResService.hasResRefThis(id)) {
            return ActResult.fail("有资源引用，无法删除！");
        }
        resService .deleteRealById(id , false);
        return ActResult.success();
    }

    @RequestMapping("batchremove")
    @ResponseBody
    public ActResult remove(String ids) {
       // resService.deleteByIds(ids);
        return ActResult.success();
    }

    @RequestMapping("edit")
    @ResponseBody
    public ActResult edit(@RequestBody MRes res) {
        resService.createOrUpdate(res);
        return ActResult.success(res);
    }


    @RequestMapping("add")
    @ResponseBody
    public ActResult add(@RequestBody MRes res) {
        res.setType(MResType.role.getType());
        res.setStatus(0);
        resService.createOrUpdate(res);
        return ActResult.success(res);
    }

    @RequestMapping("setType")
    @ResponseBody
    public ActResult setType(String ids , String type) {
        resService.setTypeByIds(ids , type);
        return ActResult.success();
    }

    @RequestMapping("getMenuByRoleId")
    @ResponseBody
    public ActResult getMenuByRoleId(Long roleId) {
        List<MRes> list = resService.getRelResByResId(roleId , MResType.menu.getType());
        return ActResult.success(list);
    }

    @RequestMapping("saveRoleMenu")
    @ResponseBody
    public ActResult saveRoleMenu(Long roleId , String ids) {
        relResService.delResRelByRef(roleId);
        if (StringUtils.isEmpty(ids)) {
            return ActResult.success();
        }
        relResService.saveResRels(roleId , ids);
        return ActResult.success();
    }

}
