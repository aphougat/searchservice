package com.cosmoneural.search.searchservice.model;

import lombok.Data;

@Data
public class UserModel {

    String id;
    String name;
    String city;
    String state;
    boolean brand;
    boolean influencer;
    String keywords;

}
