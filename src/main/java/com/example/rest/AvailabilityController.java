package com.example.acuity.controller;

import com.example.acuity.model.AvailabilityDate;
import com.example.acuity.model.AvailabilityTime;
import com.example.acuity.service.AcuityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {
    private final AcuityService acuityService;

    public AvailabilityController(AcuityService acuityService) {
        this.acuityService = acuityService;
    }

    @GetMapping("/dates/{serviceId}")
    public List<AvailabilityDate> getAvailableDates(@PathVariable int serviceId) {
        return acuityService.getAvailableDates(serviceId);
    }

    @GetMapping("/times/{serviceId}/{date}")
    public List<AvailabilityTime> getAvailableTimes(@PathVariable int serviceId, @PathVariable String date) {
        return acuityService.getAvailableTimes(serviceId, date);
    }
}
