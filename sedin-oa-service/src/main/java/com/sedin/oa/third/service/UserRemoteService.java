package com.sedin.oa.third.service;

import com.sedin.dto.UserIdentity;
import com.sedin.util.ActResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liuhan on 2017-10-21.
 */
@FeignClient(name= "uc")
public interface UserRemoteService {
    @PostMapping(value = "/thirdLogin")
    ActResult login(@RequestBody UserIdentity user);
}
