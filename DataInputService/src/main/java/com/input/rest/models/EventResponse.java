package com.input.rest.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventResponse implements Serializable {

    @JsonProperty(value = "eventId")
    private String eventId;

    @JsonProperty(value = "eventName")
    private String eventName;

    @JsonProperty(value = "eventSourceService")
    private String eventSourceService;
    
    @JsonProperty(value = "eventCreationDate")
    private Date eventCreationDate;

    @JsonProperty(value = "eventGroupId")
    private String eventGroupId;

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

    public String getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(String eventGroupId) {
        this.eventGroupId = eventGroupId;
    }
}
