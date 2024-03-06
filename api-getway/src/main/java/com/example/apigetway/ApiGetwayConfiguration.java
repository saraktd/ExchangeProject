package com.example.apigetway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.ref.PhantomReference;
import java.util.function.Function;

@Configuration
public class ApiGetwayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/get")
                        .filters(f->f.addRequestHeader("MyHeader","MyUrl")
                                .addRequestParameter("Param","Value"))
                        .uri("http://httpbin.org:80"))
                .route(p-> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p-> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p-> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p-> p.path("/currency-conversion-new/**")
                        .filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                //با این فیلتر مسیر از نیو به فین تغییر پیدا میکنه
                                //ری رایت برای تغییر مسیر به کار میره
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
