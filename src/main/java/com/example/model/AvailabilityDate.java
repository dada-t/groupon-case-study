package com.example.acuity.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailabilityDate {
    private String date;

    @JsonProperty("date")
    public String getDate() { return date; }

    @JsonProperty("date")
    public void setDate(String date) { this.date = date; }
}
