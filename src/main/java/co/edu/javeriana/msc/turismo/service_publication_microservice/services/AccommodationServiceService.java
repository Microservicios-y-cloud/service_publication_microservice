package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccommodationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationTypeRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.LocationRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationServiceService {

    private final AccommodationServiceRepository accommodationServiceRepository;
    private final AccommodationServiceMapper accommodationServiceMapper;
    private final SuperServiceMapper superServiceMapper;
    private final ServicesQueueService servicesQueueService;
    private final LocationRepository locationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;

    public Long createService(@Valid AccommodationServiceRequest request) {
        var service = accommodationServiceMapper.toAccomodationService(request);
        if (!locationRepository.existsById(service.getDestination().getId())) {
            throw new EntityNotFoundException("Location not found");
        }
        if (!accommodationTypeRepository.existsById(service.getType().getId())) {
            throw new EntityNotFoundException("Accommodation type not found");
        }
        AccommodationService accommodationService = accommodationServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(accommodationService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Accomodation service sent to queue to {}: {}", CRUDEventType.CREATE, superService);

        return accommodationService.getId();
    }

    public void deleteService(Long id) {
        var service = accommodationServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.DELETE, superService));
        log.info("Accomodation service sent to queue to {}: {}", CRUDEventType.DELETE, superService);
        accommodationServiceRepository.deleteById(id);
    }

    public void updateService(@Valid AccommodationServiceRequest request) {
        var accommodationService = accommodationServiceRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        mergerService(accommodationService, request);
        var service = accommodationServiceRepository.save(accommodationService);
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.UPDATE, superService));
        log.info("Accomodation service sent to queue to {}: {}", CRUDEventType.UPDATE, superService);
    }

    private void mergerService(AccommodationService accommodationService, @Valid AccommodationServiceRequest request) {
        if (request.destination() != null) {
            accommodationService.setDestination(locationRepository.findById(request.destination().id()).orElseThrow(() -> new EntityNotFoundException("Location not found")));
        }
        if (request.accommodationType() != null) {
            accommodationService.setType(accommodationTypeRepository.findById(request.accommodationType().accomodationTypeId()).orElseThrow(() -> new EntityNotFoundException("Accommodation type not found")));
        }
        if(StringUtils.isNotBlank(request.name())){
            accommodationService.setName(request.name());
        }
        if(StringUtils.isNotBlank(request.supplierId())){
            accommodationService.setCreatedBy(request.supplierId());
        }
        if(request.capacity() != null){
            accommodationService.setCapacity(request.capacity());
        }
        if(request.startDate() != null){
            accommodationService.setStartDate(request.startDate());
        }
        if(request.endDate() != null){
            accommodationService.setEndDate(request.endDate());
        }
        if(request.unitValue() != null){
            accommodationService.setUnitValue(request.unitValue());
        }
        accommodationService.setDescription(request.description());
    }
}
