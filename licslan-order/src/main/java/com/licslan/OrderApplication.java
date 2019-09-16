package com.licslan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LICSLAN
 *
 */

@EnableEurekaClient
@SpringBootApplication
public class OrderApplication
{
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}


//@SpringCloudApplication包含下面的几个注解

//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
//public @interface SpringCloudApplication {
//}