package com.sedin.uc.service.impl;

import com.sedin.type.MResType;
import com.sedin.util.constant.RedisKey;
import com.sedin.util.DateUtil;
import com.sedin.util.redis.RedisUtil;
import com.sedin.model.MRes;
import com.sedin.uc.service.MenuService;
import com.sedin.uc.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 系统菜单服务类，初始化放入redis
 */
@Component("menuService")
public class MenuServiceImpl implements MenuService  {
    /**
     * 转化成前端组件
     * @param menuList
     * @return
     */
    public List<Map> menuToComp(List<MRes> menuList) {
        List<Map> result = new ArrayList<>();

        Map main = new HashMap();
        main.put("path", "/");
        main.put("name", "首页");
        main.put("iconCls", "el-icon-setting");
        List<Map> tempList = new ArrayList<>();
        Map temp  = new HashMap();
        temp.put("path", "/main");
        temp.put("filePath", "func/Main.vue");
        temp.put("name", "首页");
        temp.put("hidden", true);
        tempList.add(temp);
        main.put("leaf" , true);
        main.put("hidden" , true);
        main.put("children", tempList);
        result.add(main);

        for (MRes mRes : menuList) {
            if (mRes.getParentId() == 0) {
                Map map = new HashMap();
                List<Map> children = new ArrayList<>();

                if ("会员管理".equals(mRes.getName())) {
                    System.out.println("");
                }

                addChildren(menuList  , children  , mRes);

                map.put("path", "/");
                map.put("name", mRes.getName());
                map.put("iconCls", "el-icon-setting");
                if (children.size() == 0) {
                    Map child = new HashMap();
                    child.put("path", mRes.getUrl());
                    child.put("filePath", mRes.getFilePath());
                    child.put("name", mRes.getName());
                    children.add(child);
                    map.put("leaf" , true);
                }
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }


    private void addChildren(List<MRes> menuList , List<Map> children, MRes mRes) {
        for (MRes temp : menuList) {
            if (temp.getParentId().equals(mRes.getId())) {
                Map child = new HashMap();
                child.put("path", temp.getUrl());
                child.put("filePath", temp.getFilePath());
                child.put("name", temp.getName());
                children.add(child);
            }
        }
    }
}
