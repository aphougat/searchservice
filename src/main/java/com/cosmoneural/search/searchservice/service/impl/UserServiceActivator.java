package com.cosmoneural.search.searchservice.service.impl;

import com.cosmoneural.search.searchservice.model.UserModel;
import com.cosmoneural.search.searchservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Optional;

@Component
@EnableIntegration
public class UserServiceActivator {

    @Resource
    UserRepository userRepository;

    @Resource
    ObjectMapper jacksonObjectMapper;

    @ServiceActivator(inputChannel = "user", async = "true")
    public void consumeContent(Optional<String> message) throws JsonProcessingException {
        if (message.isPresent()) {
            byte[] decodedBytes = Base64.getDecoder().decode(message.get());
            String decodedString = new String(decodedBytes);
            UserModel userModel = jacksonObjectMapper.readValue(decodedString, UserModel.class);
            userRepository.save(userModel);
        }
    }

}
