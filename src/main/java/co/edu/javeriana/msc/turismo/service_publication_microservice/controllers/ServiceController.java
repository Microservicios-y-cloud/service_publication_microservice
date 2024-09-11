package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.AccomodationServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class AccomodationServiceController {
    private final AccomodationServiceService accomodationServiceService;

    @PostMapping
    public ResponseEntity<Integer> createService(
            @RequestBody @Valid ServiceRequest request) {
        return ResponseEntity.ok(accomodationServiceService.createService(request));
    }

    @GetMapping("/{accomodation-service-id}")
    public ResponseEntity<ServiceResponse> getService(
            @PathVariable("accomodation-service-id") Long accomodationServiceId) {
        return ResponseEntity.ok(accomodationServiceService.findById(accomodationServiceId));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll(){
        return ResponseEntity.ok(accomodationServiceService.findAll());
    }
}
