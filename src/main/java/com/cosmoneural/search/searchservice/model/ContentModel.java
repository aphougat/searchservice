package com.cosmoneural.search.searchservice.model;


import lombok.Data;

import java.util.List;

@Data
public class ContentModel {
    String id;
    String title;
    String desc;
    String contentUrl;
    String mediaUrl;
    List<String> keywords;
    UserModel user;
    String contentText;
    List<String> productId;
    List<String> productNames;
}
