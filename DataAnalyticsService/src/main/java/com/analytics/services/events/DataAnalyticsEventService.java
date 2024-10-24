package com.analytics.services.events;

import org.springframework.stereotype.Service;

import com.analytics.events.DataAnalyticsTrendsEvent;

@Service
public class DataAnalyticsEventService {

    private final DataAnalyticsEventProdService dataAnalyticsEventProdService;
    private final DataInputEventConsService dataInputEventConsService;

    public DataAnalyticsEventService(
        DataAnalyticsEventProdService dataAnalyticsEventProdServ,
        DataInputEventConsService dataInputEventConsServ) {

        this.dataAnalyticsEventProdService = dataAnalyticsEventProdServ;
        this.dataInputEventConsService = dataInputEventConsServ;
    }

    public DataAnalyticsTrendsEvent sendDataAnalyticsTrendsEventProc(
        DataAnalyticsTrendsEvent dataAnalyticsTrendsEvent) {

        return dataAnalyticsEventProdService.sendDataAnalyticsTrendsEvent(
            dataAnalyticsTrendsEvent);
    }

    // public DataInputEvent getDataInputEventProc(String eventId) {

    //     return dataInputEventConsService.getDataInputEvent(eventId);
    // }
}
