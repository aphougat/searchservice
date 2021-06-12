package com.cosmoneural.search.searchservice.config;

import com.cosmoneural.search.searchservice.scope.TenantScope;
import com.cosmoneural.search.searchservice.tenant.TenantHolder;
import com.cosmoneural.search.searchservice.tenant.ThreadLocalTenantHolder;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantScopeConfig {


    @Bean
    public static TenantHolder tenantHolder()
    {
        return new ThreadLocalTenantHolder("tcp");
    }

    @Bean
    public static CustomScopeConfigurer tenantScope(TenantHolder tenantHolder)
    {
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
        scopeConfigurer.addScope("tenant", new TenantScope(tenantHolder));
        return scopeConfigurer;
    }

}
