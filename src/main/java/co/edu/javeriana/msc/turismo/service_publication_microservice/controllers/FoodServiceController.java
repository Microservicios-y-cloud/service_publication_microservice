package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.FoodServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
