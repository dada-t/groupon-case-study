package com.example.acuity.service;

import com.example.acuity.model.Appointment;
import com.example.acuity.model.AvailabilityDate;
import com.example.acuity.model.AvailabilityTime;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class AcuityService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_BASE_URL = "https://acuityscheduling.com/api/v1/";
    private static final String USER_ID = "USER_ID";
    private static final String API_KEY = "API_KEY";

    private HttpHeaders createHeaders() {
        String auth = USER_ID + ":" + API_KEY;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public List<Appointment> getAllAppointments() {
        String url = API_BASE_URL + "appointments";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<List<Appointment>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing appointments response", e);
        }
    }

    public List<AvailabilityDate> getAvailableDates(int serviceId) {
        String url = API_BASE_URL + "availability/dates?appointmentTypeID=" + serviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<List<AvailabilityDate>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing available dates", e);
        }
    }

    public List<AvailabilityTime> getAvailableTimes(int serviceId, String date) {
        String url = API_BASE_URL + "availability/times?appointmentTypeID=" + serviceId + "&date=" + date;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<List<AvailabilityTime>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing available times", e);
        }
    }

    public String bookAppointment(Appointment appointment) {
        String url = API_BASE_URL + "appointments";

        String requestBody = String.format(
                "{ \"datetime\": \"%s\", \"appointmentTypeID\": %d, \"firstName\": \"%s\", \"lastName\": \"%s\", \"notes\": \"%s\" }",
                appointment.getDateTime(),
                appointment.getAppointmentTypeID(),
                appointment.getFirstName(),
                appointment.getLastName(),
                appointment.getNotes()
        );

        HttpEntity<String> entity = new HttpEntity<>(requestBody, createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

}
