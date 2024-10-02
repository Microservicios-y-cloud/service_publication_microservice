package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.LocationMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.LocationDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.LocationRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final ServicesQueueService servicesQueueService;

    public Long createLocation(LocationResponse request) {
        var location = locationMapper.toLocation(request);
        Location locationEntity = locationRepository.save(location);
        var locationDB = locationMapper.toLocationResponse(locationEntity);
        servicesQueueService.sendLocation(new LocationDTO(LocalDateTime.now(), CRUDEventType.CREATE, locationDB));

        log.info("Location created: {}", locationEntity);
        return locationEntity.getId();
    }

}
