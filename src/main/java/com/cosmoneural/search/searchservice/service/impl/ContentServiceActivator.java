package com.cosmoneural.search.searchservice.service.impl;

import com.cosmoneural.search.searchservice.model.ContentModel;
import com.cosmoneural.search.searchservice.repository.ContentRepository;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class ContentServiceActivator {

    @Resource
    ContentRepository contentRepository;

    @ServiceActivator(inputChannel = "content")
    public void consumeContent(Optional<ContentModel> payload)
    {
        payload.ifPresent(contentModel -> contentRepository.save(contentModel));
    }

}
