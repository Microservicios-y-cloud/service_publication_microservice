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
import jakarta.ws.rs.ServiceUnavailableException;
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

    public LocationResponse createLocation(LocationResponse request) {
        var location = locationMapper.toLocation(request);
        Location locationEntity = locationRepository.save(location);
        var locationDB = locationMapper.toLocationResponse(locationEntity);
        var sent = servicesQueueService.sendLocation(new LocationDTO(LocalDateTime.now(), CRUDEventType.CREATE, locationDB));
        if(sent) {
            log.info("Location sent to queue to {}: {}", CRUDEventType.CREATE, locationDB);
        } else {
            log.error("Error sending location to queue to {}: {}", CRUDEventType.CREATE, locationDB);
            throw new ServiceUnavailableException("Error sending location to queue. Kafka service is currently unavailable. Please try again later.");
        }
        return locationDB;
    }

}
