package com.cosmoneural.search.searchservice.model.init;

public class Initializer {

    String collectionName;

    String [] uniqueKey;


    public String[] getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String[] uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

}
