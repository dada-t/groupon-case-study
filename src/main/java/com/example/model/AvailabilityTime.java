package com.example.acuity.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailabilityTime {
    private String time;

    @JsonProperty("time")
    public String getTime() { return time; }

    @JsonProperty("time")
    public void setTime(String time) { this.time = time; }
}
