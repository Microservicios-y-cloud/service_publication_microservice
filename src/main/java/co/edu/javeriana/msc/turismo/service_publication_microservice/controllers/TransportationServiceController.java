package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.TransportationServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/services/transportation")
@RequiredArgsConstructor
public class TransportationServiceController {
    private final TransportationServiceService transportationServiceService;

    @PostMapping
    public ResponseEntity<Long> createService(
            @RequestBody @Valid TransportationServiceRequest request) {
        return ResponseEntity.ok(transportationServiceService.createService(request));
    }
}
