package com.sedin.uc.config.security.handler.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhan on 2017/7/4.
 */
public class RequestUtils {

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
