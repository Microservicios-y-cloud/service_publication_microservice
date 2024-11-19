package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/services/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    /*MÉTODOS POST, PUT, DELETE (NO GET)*/
    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationResponse request) {
        return ResponseEntity.ok(locationService.createLocation(request));
    }
    

}
