package com.astraser.code.challenge.gateway.filters;

import com.astraser.code.challenge.gateway.analytics.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    @Autowired
    private RequestCounterService requestCounterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        requestCounterService.incrementCounter(exchange.getRequest().getPath().value());
        return chain.filter(exchange);
    }


}
