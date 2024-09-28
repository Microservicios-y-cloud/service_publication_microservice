package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.TransportTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/services/TransportType")
@RequiredArgsConstructor
public class TransportTypeController {
    private final TransportTypeService TransportTypeService;
    /*MÉTODOS POST, PUT, DELETE (NO GET)*/
}
