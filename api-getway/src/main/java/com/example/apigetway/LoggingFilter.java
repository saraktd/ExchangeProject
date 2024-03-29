package com.example.apigetway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



public class LoggingFilter implements GlobalFilter {
    private Logger logger= LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("path of the rquest recevied->{}",exchange.getRequest().getPath());
        return  chain.filter(exchange);
    }
}
