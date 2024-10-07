package com.input.services.events;

import java.util.Date;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.input.constants.AppConstants;
import com.input.events.DataInputEvent;

@Service
public class DataInputEventProdService {

    private final KafkaTemplate<String, DataInputEvent> kT;

    public DataInputEventProdService(KafkaTemplate<String, DataInputEvent> kafkaTemplate) {

        this.kT = kafkaTemplate;
    }

    public DataInputEvent sendDataInputEvent(DataInputEvent dataInputEvent) {

        final String dataInputEventTopic = AppConstants.DATA_INPUT_EVENT_TOPIC;
        final String dataInputEventGroupId = AppConstants.DATA_INPUT_EVENT_GROUP_ID;

        String dataInputEventId = UUID.randomUUID().toString();
        String dataInputEventName = "DataInputEvent";
        String dataInputEventSourceServ = "DataInputService";
        Date dataInputEventCreationDate = new Date();

        dataInputEvent.setEventId(dataInputEventId);
        dataInputEvent.setEventName(dataInputEventName);
        dataInputEvent.setEventSourceService(dataInputEventSourceServ);
        dataInputEvent.setEventCreationDate(dataInputEventCreationDate);
        dataInputEvent.setEventGroupId(dataInputEventGroupId);
        kT.send(dataInputEventTopic, dataInputEvent);

        return dataInputEvent;
    }
}
