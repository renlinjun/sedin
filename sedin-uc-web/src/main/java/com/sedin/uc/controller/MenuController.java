package com.sedin.uc.controller;

import com.github.pagehelper.PageInfo;
import com.sedin.model.MRes;
import com.sedin.type.MResType;
import com.sedin.uc.service.RelResService;
import com.sedin.uc.service.ResService;
import com.sedin.util.ActResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuhan on 2017-11-03.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    ResService resService;

    @Autowired
    RelResService relResService;

    @RequestMapping("getMenu")
    @ResponseBody
    public ActResult getMenu(Long parentId) {
        return ActResult.success(resService.getResByParentId(parentId, MResType.menu.getType()));
    }



    @RequestMapping("remove")
    @ResponseBody
    public ActResult remove(Long id) {
        if (id == null || id == 0 ) return ActResult.fail("参数为空！");
//        if (relResService.hasResRefThis(id)) {
//            return ActResult.fail("有资源引用，无法删除！");
//        }
        resService.deleteRealById(id , true);
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
        res.setType(MResType.menu.getType());
        res.setStatus(0);
        res.setIsLeaf("0");
        resService.createOrUpdate(res);
        return ActResult.success(res);
    }

}
