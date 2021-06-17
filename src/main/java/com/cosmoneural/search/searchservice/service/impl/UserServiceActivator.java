package com.cosmoneural.search.searchservice.service.impl;

import com.cosmoneural.search.searchservice.model.UserModel;
import com.cosmoneural.search.searchservice.repository.UserRepository;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class UserServiceActivator {

    @Resource
    UserRepository userRepository;

    @ServiceActivator(inputChannel = "user")
    public void consumeContent(Optional<UserModel> payload)
    {
        payload.ifPresent(userModel -> userRepository.save(userModel));
    }

}
