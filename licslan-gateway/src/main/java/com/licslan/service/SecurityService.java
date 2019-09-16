package com.licslan.service;

import com.licslan.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "LICSLAN-RBAC", fallback = SecurityServiceHystric.class)
public interface SecurityService {

    /**
     * 校验身份
     *
     * @return
     */
    @PostMapping(value =  "/licslan/rbac/security/auth")
    Result auth(@RequestParam("url") String url, @RequestHeader(value = "token") String token);


}
