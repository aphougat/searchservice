package com.cosmoneural.search.searchservice.model.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;

import java.util.function.Consumer;

public interface Channels {


    @Bean
    MessageChannel content();

    @Bean
    MessageChannel user();

}
