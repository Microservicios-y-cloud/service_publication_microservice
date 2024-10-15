package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.TransportationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.LocationRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportTypeRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportationServiceRepository;
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
public class TransportationServiceService {
    private final TransportationServiceRepository transportationServiceRepository;
    private final TransportationServiceMapper transportationServiceMapper;
    private final SuperServiceMapper superServiceMapper;
    private final ServicesQueueService servicesQueueService;
    private final LocationRepository locationRepository;
    private final TransportTypeRepository transportTypeRepository;
    public Long createService(TransportationServiceRequest request) {
        var service = transportationServiceMapper.toTransportationService(request);
        if(!locationRepository.existsById(service.getOrigin().getId())) {
            throw new EntityNotFoundException("Origin location not found");
        }
        if(!locationRepository.existsById(service.getDestination().getId())) {
            throw new EntityNotFoundException("Destination location not found");
        }
        if(!transportTypeRepository.existsById(service.getTransportType().getId())) {
            throw new EntityNotFoundException("Transport type location not found");
        }
        TransportationService transportationService = transportationServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(transportationService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Transportation service sent to queue to {}: {}", CRUDEventType.CREATE, superService);
        return transportationService.getId();
    }

    public void deleteService(Long id) {
        var service = transportationServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.DELETE, superService));
        log.info("Transportation service sent to queue to {}: {}", CRUDEventType.DELETE, superService);
        transportationServiceRepository.deleteById(id);
    }

    public void updateService(@Valid TransportationServiceRequest request) {
        var transportationService = transportationServiceRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        mergerService(transportationService, request);
        var service = transportationServiceRepository.save(transportationService);
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.UPDATE, superService));
        log.info("Transportation service sent to queue to {}: {}", CRUDEventType.UPDATE, superService);
    }

    private void mergerService(TransportationService transportationService, @Valid TransportationServiceRequest request) {
        if (request.destination() != null) {
            transportationService.setDestination(locationRepository.findById(request.destination().id()).orElseThrow(() -> new EntityNotFoundException("Location not found")));
        }
        if (request.origin() != null) {
            transportationService.setOrigin(locationRepository.findById(request.origin().id()).orElseThrow(() -> new EntityNotFoundException("Location not found")));
        }
        if (request.transportType() != null) {
            transportationService.setTransportType(transportTypeRepository.findById(request.transportType().transportTypeId()).orElseThrow(() -> new EntityNotFoundException("Transportation type not found")));
        }
        if(StringUtils.isNotBlank(request.name())){
            transportationService.setName(request.name());
        }
        if(StringUtils.isNotBlank(request.supplierId())){
            transportationService.setCreatedBy(request.supplierId());
        }
        if(request.startDate() != null){
            transportationService.setStartDate(request.startDate());
        }
        if(request.endDate() != null){
            transportationService.setEndDate(request.endDate());
        }
        if(request.unitValue() != null){
            transportationService.setUnitValue(request.unitValue());
        }
        if (request.company() != null) {
            transportationService.setCompany(request.company());
        }
        transportationService.setDescription(request.description());
    }
}
