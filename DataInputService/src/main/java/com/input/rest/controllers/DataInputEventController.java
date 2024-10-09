package com.input.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.input.constants.AppConstants;
import com.input.events.DataInputEvent;
import com.input.mappers.DataInputEventMapper;
import com.input.rest.models.DataInputEventRequest;
import com.input.rest.models.DataInputEventResponse;
import com.input.services.events.DataInputEventService;

@RestController
@RequestMapping(AppConstants.REST)
public class DataInputEventController {

    private final DataInputEventService dataInputEventService;

    public DataInputEventController(DataInputEventService dataInputEventServ) {

        this.dataInputEventService = dataInputEventServ;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping( value = "data/input/events")
    public DataInputEventResponse createDataInputEvent(
        @Validated
        @RequestBody DataInputEventRequest dataInputEventRequest) {

            DataInputEventResponse dataInputEventResponse;

            DataInputEvent dataInputEvent = DataInputEventMapper.toEvent.apply(
                dataInputEventRequest);

            dataInputEventResponse = DataInputEventMapper.toEventResp.apply(
                dataInputEventService.sendDataInputEventProc(dataInputEvent));
            
            return dataInputEventResponse;
    }

    // @GetMapping(value = "data/input/events/{eventId}")
    // public DataInputEventResponse fetchUserById(
    //         @Validated
    //         @PathVariable(value = "eventId") String eventId) {

    //     return DataInputEventMapper.toEventResp.apply(
    //         dataInputEventService.getDataInputEventProc(eventId));
    // }
}
