package com.licslan.service;

import com.licslan.result.Result;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceHystric implements SecurityService {


    @Override
    public Result auth(String url, String token) {
        return new Result().error(500, "暂无权限");
    }
}
