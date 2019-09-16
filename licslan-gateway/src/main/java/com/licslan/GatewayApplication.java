package com.licslan;

import com.licslan.config.AuthGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author LICSLAN
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication
{
    @Autowired
    public GatewayApplication(AuthGatewayFilter authGatewayFilter) {
        this.authGatewayFilter = authGatewayFilter;
    }

    public static void main(String[] args )
    {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 全局身份权限拦截器
     */
    private final AuthGatewayFilter authGatewayFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("licslan-order", r -> r.path("/licslan/order/**").uri("lb://LICSLAN-ORDER").filter(authGatewayFilter))
                .route("licslan-product", r -> r.path("/licslan/product/**").uri("lb://LICSLAN-PRODUCT").filter(authGatewayFilter))
                .route("licslan-openapi", r -> r.path("/licslan/openapi/**").uri("lb://LICSLAN-OPENAPI").filter(authGatewayFilter))
                .route("licslan-middleware", r -> r.path("/licslan/middleware/**").uri("lb://LICSLAN-MIDDLEWARE").filter(authGatewayFilter))
                .route("licslan-eureka", r -> r.path("/licslan/registry/**").uri("lb://LICSLAN-REGISTER"))
                .build();
    }
}
