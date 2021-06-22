package com.cosmoneural.search.searchservice.config;

import com.cosmoneural.search.searchservice.scope.TenantScoped;
import com.cosmoneural.search.searchservice.tenant.TenantHolder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import javax.annotation.Resource;

@Configuration
@EntityScan("com.cosmoneural.search")
public class MongoConfig extends AbstractReactiveMongoConfiguration {


    @Resource
    TenantHolder tenantHolder;

    @Resource
    private Environment environment;

    @TenantScoped
    @Override
    protected String getDatabaseName() {
        return environment.getProperty(String.format("com.cosmoneural.search.%s.mongo.dbname",tenantHolder.getTenant()));
    }

    @TenantScoped
    @Override
    public MongoClient reactiveMongoClient() {
        ConnectionString connectionString = new ConnectionString(environment.getProperty(String.format("com.cosmoneural.search.%s.mongo.connection.url",tenantHolder.getTenant())));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @TenantScoped
    @Primary
    public ReactiveMongoDatabaseFactory mongoDbFactory() {
        return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), getDatabaseName());
    }

}
