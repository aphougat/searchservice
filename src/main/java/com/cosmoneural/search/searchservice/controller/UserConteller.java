package com.cosmoneural.search.searchservice.controller;

import com.cosmoneural.search.searchservice.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserConteller {

    @Resource
    MessageChannel user_output;

    @Resource
    ObjectMapper jacksonObjectMapper;

    @PostMapping("/user")
    public void passUSer(@RequestBody UserModel userModel) throws JsonProcessingException {
        String payload = jacksonObjectMapper.writeValueAsString(userModel);
        user_output.send(MessageBuilder.withPayload(payload).build());
    }

}
