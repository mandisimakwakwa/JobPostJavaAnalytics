package com.input.events;

import java.util.Date;

abstract class Event {
    
    private String eventId;
    private String eventName;
    private String eventSourceService;
    private Date eventCreationDate;
    
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventSourceService() {
        return eventSourceService;
    }
    public void setEventSourceService(String eventSourceService) {
        this.eventSourceService = eventSourceService;
    }

    public Date getEventCreationDate() {
        return eventCreationDate;
    }
    public void setEventCreationDate(Date eventCreationDate) {
        this.eventCreationDate = eventCreationDate;
    }
}
