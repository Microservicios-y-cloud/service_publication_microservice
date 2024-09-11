package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.ServiceService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.TransportationServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{service-id}")
    public ResponseEntity<TransportationServiceResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(transportationServiceService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<TransportationServiceResponse>> findAll(){
        return ResponseEntity.ok(transportationServiceService.findAll());
    }
}
