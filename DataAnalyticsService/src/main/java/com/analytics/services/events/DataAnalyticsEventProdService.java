package com.analytics.services.events;

import java.util.Date;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.analytics.constants.AppConstants;
import com.analytics.events.DataAnalyticsTrendsEvent;

@Service
public class DataAnalyticsEventProdService {

    private final KafkaTemplate<String, DataAnalyticsTrendsEvent> kT;

    public DataAnalyticsEventProdService(KafkaTemplate<String, DataAnalyticsTrendsEvent> kafkaTemplate) {

        this.kT = kafkaTemplate;
    }

    public DataAnalyticsTrendsEvent sendDataAnalyticsTrendsEvent(DataAnalyticsTrendsEvent dataAnalyticsTrendsEvent) {

        final String dataAnalyticsTrendsEventTopic = AppConstants.DATA_ANALYTICS_TRENDS_EVENT_TOPIC;
        final String dataAnalyticsTrendsEventGroupId = AppConstants.DATA_ANALYTICS_TRENDS_EVENT_GROUP_ID;

        String dataAnalyticsTrendsEventId = UUID.randomUUID().toString();
        String dataAnalyticsTrendsEventName = "DataAnalyticsTrendsEvent";
        String dataAnalyticsTrendsEventSourceServ = "DataAnalyticsService";
        Date dataAnalyticsTrendsEventCreationDate = new Date();

        dataAnalyticsTrendsEvent.setEventId(dataAnalyticsTrendsEventId);
        dataAnalyticsTrendsEvent.setEventName(dataAnalyticsTrendsEventName);
        dataAnalyticsTrendsEvent.setEventSourceService(dataAnalyticsTrendsEventSourceServ);
        dataAnalyticsTrendsEvent.setEventCreationDate(dataAnalyticsTrendsEventCreationDate);
        dataAnalyticsTrendsEvent.setEventGroupId(dataAnalyticsTrendsEventGroupId);

        kT.send(dataAnalyticsTrendsEventTopic, dataAnalyticsTrendsEvent);

        return dataAnalyticsTrendsEvent;
    }
}
