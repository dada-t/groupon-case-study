package com.example.acuity.controller;

import com.example.acuity.model.Appointment;
import com.example.acuity.service.AcuityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AcuityService acuityService;

    public AppointmentController(AcuityService acuityService) {
        this.acuityService = acuityService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return acuityService.getAllAppointments();
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestBody Appointment appointment) {
        return acuityService.bookAppointment(appointment);
    }
}
