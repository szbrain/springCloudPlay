package com.licslan.config;

import com.alibaba.fastjson.JSON;
import com.licslan.result.Result;
import com.licslan.service.SecurityService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  身份权限拦截器
 **/
@Component
@Data
public class AuthGatewayFilter implements GatewayFilter, Ordered {

    private final SecurityService securityService;

    final
    RestTemplate restTemplate;

    protected  static final String TOKEN ="token";

    @Autowired
    public AuthGatewayFilter(SecurityService securityService, RestTemplate restTemplate) {
        this.securityService = securityService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest exRequest = exchange.getRequest();

        String token=getToken(exRequest);

        Result result = securityService.auth(exRequest.getPath().toString(),token);

        if (!result.success()) {
            return authFail(exchange.getResponse(), new Result().error(401, "登录失效，请重新登录"));
        }

        ServerHttpRequest request = exchange.getRequest().mutate().header(TOKEN, token).build();

        ServerHttpResponse response = exchange.getResponse();

        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(request).response(response).build();
        return chain.filter(build);
    }

    /**
     * 获取token机制
     * 1：优先从header中取
     * 2：其次从cookies中取
     * 3：再从请求参数中去
     */
    private String getToken(ServerHttpRequest exRequest) {
        HttpHeaders httpHeaders = exRequest.getHeaders();
        List<String> headerTokens = httpHeaders.get(TOKEN);
        if (!CollectionUtils.isEmpty(headerTokens)) {
            return headerTokens.get(0);
        }

        HttpCookie tokenCookies = exRequest.getCookies().getFirst(TOKEN);
        if (tokenCookies != null) {
            return tokenCookies.getValue();
        }

        String token = exRequest.getQueryParams().getFirst(TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }

        return "";
    }

    /**
     * 返回失败
     */
    private Mono<Void> authFail(ServerHttpResponse response, Result result) {
        byte[] datas = JSON.toJSONBytes(result);
        DataBuffer buffer = response.bufferFactory().wrap(datas);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
