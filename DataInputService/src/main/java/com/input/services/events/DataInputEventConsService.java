package com.input.services.events;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.input.constants.AppConstants;
import com.input.events.DataInputEvent;

@Service
public class DataInputEventConsService {

    private final String dataInputEventTopic = AppConstants.DATA_INPUT_EVENT_TOPIC;
    private final String dataInputEventGroupId = AppConstants.DATA_INPUT_EVENT_GROUP_ID;

    private ConcurrentHashMap<String, DataInputEvent> dataInputEventInMemStore = new ConcurrentHashMap<>();

    @KafkaListener(topics = dataInputEventTopic, groupId = dataInputEventGroupId)
    public void consumeDataInputEvent(String dataInputEventJSON) {

        ObjectMapper objectMapper = new ObjectMapper();

        DataInputEvent dataInputEvent = new DataInputEvent();
        try {

            System.out.println("json event val : " + dataInputEventJSON);

            dataInputEvent = objectMapper.readValue(
            dataInputEventJSON, DataInputEvent.class);

            String dataInputEventKey = dataInputEvent.getEventId();

            System.out.println("json conv res : " + dataInputEvent);
            System.out.println("json conv event id res : " + dataInputEventKey);
            System.out.println("json conv event name res : " + dataInputEvent.getEventName());

            System.out.println("output of event store in mem : " + dataInputEventInMemStore.put(dataInputEventKey, dataInputEvent));
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
    }

    public DataInputEvent getDataInputEvent(String eventId) {

        System.out.println("in mem print res : " + dataInputEventInMemStore.get(eventId));

        return dataInputEventInMemStore.get(eventId);
    }

    // public void getDataInputEvent(ConsumerRecord<String, DataInputEvent> dataInputEventConsRec) {

    //     System.out.println("Consumed Data Input Event Record val : " + String.valueOf(dataInputEventConsRec.value()));
    // }
}
