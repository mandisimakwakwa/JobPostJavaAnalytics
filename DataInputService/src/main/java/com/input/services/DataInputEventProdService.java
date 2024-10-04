package com.input.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.input.events.DataInputEvent;

@Service
public class DataInputEventProdService {

    private final KafkaTemplate<String, DataInputEvent> kT;

    public DataInputEventProdService(KafkaTemplate<String, DataInputEvent> kafkaTemplate) {

        this.kT = kafkaTemplate;
    }

    public void sendOrderEvent(DataInputEvent dataInputEvent) {

        String dataInputEventId = UUID.randomUUID().toString();
        String dataInputEventName = "DataInputEvent";
        String dataInputEventTopic = "data_input_event_topic";
        String dataInputEventSourceServ = "DataInputService";
        Date dataInputEventCreationDate = new Date();

        dataInputEvent.setEventId(dataInputEventId);
        dataInputEvent.setEventName(dataInputEventName);
        dataInputEvent.setEventSourceService(dataInputEventSourceServ);
        dataInputEvent.setEventCreationDate(dataInputEventCreationDate);
        kT.send(dataInputEventTopic, dataInputEvent);
    }
}
