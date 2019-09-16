package com.licslan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LICSLAN
 * 中间件服务
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MiddlewareApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MiddlewareApplication.class, args);
    }
}
