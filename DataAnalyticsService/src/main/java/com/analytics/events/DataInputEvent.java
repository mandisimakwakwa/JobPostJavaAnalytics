package com.analytics.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DataInputEvent extends Event {

    List<String[]> dataInputEventArray = new ArrayList<>();

    public List<String[]> getDataInputEventArray() {
        return dataInputEventArray;
    }

    public void setDataInputEventArray(List<String[]> dataInputEventArray) {
        this.dataInputEventArray = dataInputEventArray;
    }
}
