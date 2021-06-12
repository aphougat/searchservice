package com.cosmoneural.search.searchservice.service.impl;

import com.cosmoneural.search.searchservice.model.init.Initializer;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InitializationService {

    @Resource
    MongoDBCollectionService mongoDBCollectionService;

    public boolean initialize(List<Initializer> initializer)
    {
        initializer.forEach(initializer1 -> {
            MongoClient mongoClient = mongoDBCollectionService.getClient();
            MongoCollection<Document> collection = mongoDBCollectionService.getCollection(mongoClient,initializer1.getCollectionName());
            collection.drop();
            collection.createIndex(Indexes.ascending(initializer1.getUniqueKey()), new IndexOptions().unique(true));
        });
        return true;

    }

}
