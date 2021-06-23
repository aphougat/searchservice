package com.cosmoneural.search.searchservice.model.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channels {


    @Input
    MessageChannel content();

    @Input
    MessageChannel user();

    @Output
    MessageChannel user_output();

}
