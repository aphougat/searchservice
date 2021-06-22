package com.cosmoneural.search.searchservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

@Data
@SolrDocument(collection = "user")
public class UserModel {

    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "name", type = "string")
    private String name;
    @Indexed(name = "city", type = "string")
    String city;
    @Indexed(name = "state", type = "string")
    String state;
    @Indexed(name = "brand", type = "string")
    boolean brand;
    @Indexed(name = "influencer", type = "boolean")
    boolean influencer;
    @Indexed(name = "keywords", type = "text")
    List<String> keywords;

}
