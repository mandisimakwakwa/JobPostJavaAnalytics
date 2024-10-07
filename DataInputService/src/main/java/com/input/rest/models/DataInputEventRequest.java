package com.input.rest.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataInputEventRequest {

    @JsonProperty(value = "dataInputEventArray")
    private List<String[]> dataInputEventArray;

    public List<String[]> getDataInputEventArray() {
        return dataInputEventArray;
    }

    public void setDataInputEventArray(List<String[]> dataInputEventArray) {
        this.dataInputEventArray = dataInputEventArray;
    }
}
