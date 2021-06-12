package com.cosmoneural.search.searchservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.tatadigital.product.spring.scope.TenantScoped;
import com.tatadigital.product.tenant.TenantHolder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import javax.annotation.Resource;

@Configuration
@EntityScan("com.tatadigital.product")
public class MongoConfig extends AbstractMongoClientConfiguration {


    @Resource
    TenantHolder tenantHolder;

    @Resource
    private Environment environment;

    @TenantScoped
    @Override
    protected String getDatabaseName() {
        return environment.getProperty(String.format("com.tatadigital.product.%s.mongo.dbname",tenantHolder.getTenant()));
    }

    @TenantScoped
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(environment.getProperty(String.format("com.tatadigital.product.%s.mongo.connection.url",tenantHolder.getTenant())));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @TenantScoped
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
    }

 /*   @Override
    protected boolean autoIndexCreation() {
        return true;
    }*/
}
