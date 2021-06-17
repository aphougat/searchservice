package com.cosmoneural.search.searchservice.service.impl;

import com.cosmoneural.search.searchservice.tenant.TenantHolder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class MongoDBCollectionService {

    @Resource
    private Environment environment;

    @Resource
    TenantHolder tenantHolder;

    public MongoClient getClient()
    {
            ConnectionString uri = new ConnectionString(environment.getProperty(String.format("com.cosmoneural.search.%s.mongo.connection.url",tenantHolder.getTenant())));
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(uri)
                    .codecRegistry(codecRegistry)
                    .build();
            return MongoClients.create(clientSettings);
    }


    public <T> MongoCollection<T> getCollection(MongoClient mongoClient, Class<T> collectionClass) {

        String dbName = environment.getProperty(String.format("com.cosmoneural.search.%s.mongo.dbname",tenantHolder.getTenant()));
        MongoDatabase db = mongoClient.getDatabase(dbName);
        return db.getCollection(collectionClass.getSimpleName(), collectionClass);
    }

    public MongoCollection<Document> getCollection(MongoClient mongoClient,String collectionName) {

        String dbName = environment.getProperty(String.format("com.cosmoneural.search.%s.mongo.dbname",tenantHolder.getTenant()));
        MongoDatabase db = mongoClient.getDatabase(dbName);
        return  db.getCollection(collectionName);
    }

}
