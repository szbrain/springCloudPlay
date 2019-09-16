package com.licslan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LICSLAN
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CommonApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CommonApplication.class, args);
    }
}
