package com.input.services.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.input.constants.AppConstants;
import com.input.events.DataInputEvent;

@Service
public class DataInputEventConsService {

    private final String dataInputEventTopic = AppConstants.DATA_INPUT_EVENT_TOPIC;
    private final String dataInputEventGroupId = AppConstants.DATA_INPUT_EVENT_GROUP_ID;

    @KafkaListener(topics = dataInputEventTopic, groupId = dataInputEventGroupId)
    public void getDataInputEvent(ConsumerRecord<String, DataInputEvent> dataInputEventConsRec) {

        System.out.println("Consumed Data Input Event Record val : " + String.valueOf(dataInputEventConsRec.value()));
    }
}
