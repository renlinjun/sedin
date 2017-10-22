package com.sedin.uc.provide.service;

import com.sedin.dto.UserIdentity;
import com.sedin.util.ActResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserProvideService {
    ActResult login(@RequestBody UserIdentity user) throws Exception;

    String getUserByTicket(String ticket);
}
