package com.analytics.services.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.analytics.constants.AppConstants;
import com.analytics.events.DataInputEvent;
import com.analytics.services.DataAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataInputEventConsService {

    private final String dataInputEventTopic = AppConstants.DATA_INPUT_EVENT_TOPIC;
    private final String dataInputEventGroupId = AppConstants.DATA_INPUT_EVENT_GROUP_ID;

    private DataAnalyticsService dataAnalyticsService;

    public DataInputEventConsService(DataAnalyticsService dataAnalyticsServ) {
        this.dataAnalyticsService = dataAnalyticsServ;
    }

    @KafkaListener(topics = dataInputEventTopic, groupId = dataInputEventGroupId, concurrency = "3")
    public void consumeDataInputEvent(String dataInputEventJSON) {

        ObjectMapper objectMapper = new ObjectMapper();

        DataInputEvent dataInputEvent = new DataInputEvent();

        String dataInputEventKey = null;
        try {

            System.out.println("json event val : " + dataInputEventJSON);

            dataInputEvent = objectMapper.readValue(
            dataInputEventJSON, DataInputEvent.class);

            dataAnalyticsService.parseDataInputEvent(dataInputEvent);

            dataInputEventKey = dataInputEvent.getEventId();
        } catch (Exception e) {

            e.printStackTrace();
        }

        if (dataInputEventKey != null) {

            System.out.println("json conv res : " + dataInputEvent);
            System.out.println("json conv event id res : " + dataInputEventKey);
            System.out.println("json conv event name res : " + dataInputEvent.getEventName());
        }
    }

    // public DataInputEvent getDataInputEvent(String eventId) {

    //     System.out.println("in mem print res : " + dataInputEventInMemStore.get(eventId));

    //     return dataInputEventInMemStore.get(eventId);
    // }
}
