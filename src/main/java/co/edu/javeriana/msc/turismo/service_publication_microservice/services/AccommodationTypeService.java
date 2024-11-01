package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccommodationTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationTypeRepository;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final AccommodationTypeMapper accommodationTypeMapper;
    private final ServiceTypeMapper serviceTypeMapper;
    private final ServicesQueueService servicesQueueService;

    public Long createAccommodationType(AccommodationTypeResponse request) {
        var accommodationType = accommodationTypeMapper.toaccommodationType(request);
        AccommodationType accommodationTypeEntity = accommodationTypeRepository.save(accommodationType);
        var serviceType = serviceTypeMapper.toServiceType(accommodationTypeEntity);
        var sent = servicesQueueService.sendServiceType(new ServiceTypeDTO(LocalDateTime.now(), CRUDEventType.CREATE, serviceType));
        if (sent) {
            log.info("Accommodation type sent to queue to {}: {}", CRUDEventType.CREATE, serviceType);
        } else {
            log.error("Error sending accommodation type to queue to {}: {}", CRUDEventType.CREATE, serviceType);
            throw new ServiceUnavailableException("Error sending accommodation type to queue. Kafka service is currently unavailable. Please try again later.");
        }
        return accommodationTypeEntity.getId();
    }

    public boolean existsById(Long id) {
        return accommodationTypeRepository.existsById(id);
    }
}
