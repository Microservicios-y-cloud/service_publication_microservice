package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccomodationTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationTypeRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final AccomodationTypeMapper accommodationTypeMapper;
    private final ServiceTypeMapper serviceTypeMapper;
    private final ServicesQueueService servicesQueueService;

    public Long createAccommodationType(AccommodationTypeResponse request) {
        var accommodationType = accommodationTypeMapper.toAccomodationType(request);
        AccommodationType accommodationTypeEntity = accommodationTypeRepository.save(accommodationType);
        var serviceType = serviceTypeMapper.toServiceType(accommodationTypeEntity);
        servicesQueueService.sendServiceType(new ServiceTypeDTO(LocalDateTime.now(), CRUDEventType.CREATE, serviceType));

        log.info("Accommodation type created: {}", accommodationTypeEntity);
        return accommodationTypeEntity.getId();
    }

    public boolean existsById(Long id) {
        return accommodationTypeRepository.existsById(id);
    }
}
