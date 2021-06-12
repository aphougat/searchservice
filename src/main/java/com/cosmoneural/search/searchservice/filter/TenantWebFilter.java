package com.cosmoneural.search.searchservice.filter;

import com.cosmoneural.search.searchservice.tenant.InvalidTenantException;
import com.cosmoneural.search.searchservice.tenant.TenantHolder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class TenantWebFilter implements WebFilter {

    @Resource
    private TenantHolder tenantHolder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).doFinally(signalType -> {
            String tenantID = exchange.getRequest().getHeaders().get("X-ProgramID").get(0);
            if (tenantID == null) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                byte[] bytes = "X-ProgramID not present in the Request Header".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                exchange.getResponse().writeWith(Flux.just(buffer));
            }
            try {
                tenantHolder.setTenant(tenantID);
            } catch (InvalidTenantException e) {
                e.printStackTrace();
            }
        });
    }
}
