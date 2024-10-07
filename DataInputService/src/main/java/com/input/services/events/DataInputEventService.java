package com.input.services.events;

import org.springframework.stereotype.Service;

import com.input.events.DataInputEvent;

@Service
public class DataInputEventService {

    private final DataInputEventProdService dataInputEventProdService;
    private final DataInputEventConsService dataInputEventConsService;

    public DataInputEventService(

        DataInputEventProdService dataInputEventProdServ,
        DataInputEventConsService dataInputEventConsServ) {

        this.dataInputEventProdService = dataInputEventProdServ;
        this.dataInputEventConsService = dataInputEventConsServ;
    }

    public DataInputEvent sendDataInputEventProc(DataInputEvent dataInputEvent) {

        return dataInputEventProdService.sendDataInputEvent(dataInputEvent);
    }

    public void getDataInputEventProc() {

        dataInputEventConsService.getDataInputEvent(null);
    }
}
