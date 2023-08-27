package com.realtime.project.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtime.project.model.WebsiteDto;
import com.realtime.project.service.UserInfoService;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class KafkaListeners {


    @Autowired
    private UserInfoService userInfoService;

    @KafkaListener(topics = "WEBSITE.events", groupId = "my-consumer-group")

    public void consumeMessage(String message) {
        // Process the received message
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.readValue(message, String.class);
            WebsiteDto websiteDto = mapper.readValue(value, WebsiteDto.class);
            userInfoService.submitWebsite(Collections.singletonList(websiteDto.convert()), websiteDto.getUsername());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
