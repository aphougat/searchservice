package com.cosmoneural.search.searchservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

@Data
@SolrDocument(collection = "content")
public class ContentModel {
    @Id
    @Indexed(name = "id", type = "string")
    String id;
    @Indexed(name = "title", type = "text")
    String title;
    @Indexed(name = "desc", type = "text")
    String desc;
    @Indexed(name = "contentUrl", type = "string")
    String contentUrl;
    @Indexed(name = "mediaUrl", type = "string")
    String mediaUrl;
    @Indexed(name = "keywords", type = "text")
    List<String> keywords;
    @Indexed(name = "user", type = "string")
    String user;
    @Indexed(name = "productId", type = "text")
    List<String> productId;
    @Indexed(name = "productNames", type = "text")
    List<String> productNames;
}
