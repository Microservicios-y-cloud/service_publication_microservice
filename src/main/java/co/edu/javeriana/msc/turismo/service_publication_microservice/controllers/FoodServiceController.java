package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.ServiceService;
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
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<Integer> createService(
            @RequestBody @Valid ServiceRequest request) {
        return ResponseEntity.ok(serviceService.createService(request));
    }

    @GetMapping("/{service-id}")
    public ResponseEntity<ServiceResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(serviceService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll(){
        return ResponseEntity.ok(serviceService.findAll());
    }
}
