package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.FoodServiceService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/services/food")
@RequiredArgsConstructor
public class FoodServiceController {
    private final FoodServiceService foodServiceService;

    @PostMapping
    public ResponseEntity<Long> createService(
            @RequestBody @Valid FoodServiceRequest request) {
        return ResponseEntity.ok(foodServiceService.createService(request));
    }

    @GetMapping("/{service-id}")
    public ResponseEntity<FoodServiceResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(foodServiceService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<FoodServiceResponse>> findAll(){
        return ResponseEntity.ok(foodServiceService.findAll());
    }
}
