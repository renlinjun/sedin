package com.sedin.uc.service;

import com.sedin.model.MRes;

import java.util.List;
import java.util.Map;

public interface MenuService {
    public List<Map> menuToComp(List<MRes> menuList);
}
