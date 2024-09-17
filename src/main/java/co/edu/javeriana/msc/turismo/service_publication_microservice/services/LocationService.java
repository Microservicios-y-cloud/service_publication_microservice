package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.LocationMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.LocationRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationResponse findById(Long locationId) {
        return locationRepository.findById(locationId)
                .map(locationMapper::toLocationResponse)
                .orElseThrow(() -> new EntityNotFoundException("Service not found, with id: " + locationId));
    }

    public List<LocationResponse> findAll() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toLocationResponse)
                .collect(Collectors.toList());
    }
}
