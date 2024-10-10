package com.input.mappers;

import java.util.function.Function;

import com.input.events.DataInputEvent;
import com.input.rest.models.DataInputEventRequest;
import com.input.rest.models.DataInputEventResponse;

public class DataInputEventMapper {
    
    public static final Function<DataInputEventRequest, DataInputEvent> toEvent =
        e -> {

            DataInputEvent dataInputEvent = new DataInputEvent();
            dataInputEvent.setDataInputEventArray(e.getDataInputEventArray());
            
            return dataInputEvent;
        };

    public static final Function<DataInputEvent, DataInputEventResponse> toEventResp =
        eR -> {

            DataInputEventResponse dataInputEventResponse = new DataInputEventResponse();
            
            dataInputEventResponse.setEventId(eR.getEventId());
            dataInputEventResponse.setEventName(eR.getEventName());
            dataInputEventResponse.setEventSourceService(eR.getEventSourceService());
            dataInputEventResponse.setEventCreationDate(eR.getEventCreationDate());
            dataInputEventResponse.setEventGroupId(eR.getEventGroupId());
            dataInputEventResponse.setDataInputEventArray(eR.getDataInputEventArray());
            
            return dataInputEventResponse;
        };
}
