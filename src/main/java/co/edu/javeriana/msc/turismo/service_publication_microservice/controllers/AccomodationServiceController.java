package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.AccommodationServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/services/accommodation")
@RequiredArgsConstructor
public class AccomodationServiceController {
    private final AccommodationServiceService accomodationServiceService;

    @PostMapping
    public ResponseEntity<Long> createService(
            @RequestBody @Valid AccommodationServiceRequest request) {
        return ResponseEntity.ok(accomodationServiceService.createService(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        accomodationServiceService.deleteService(id);
        return ResponseEntity.accepted().build();
    }
    @PutMapping
    public ResponseEntity<Void> updateService(
            @RequestBody @Valid AccommodationServiceRequest request) {
        accomodationServiceService.updateService(request);
        return ResponseEntity.accepted().build();
    }
}
