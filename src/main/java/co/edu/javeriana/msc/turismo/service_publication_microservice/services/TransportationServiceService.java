package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.TransportationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportationServiceRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public Long createService(TransportationServiceRequest request) {
        var service = transportationServiceMapper.toTransportationService(request);
        TransportationService transportationService = transportationServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(transportationService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Transportation service sent to queue: {}", superService);
        return transportationService.getId();
    }

    public TransportationServiceResponse findById(Long transportationServiceId) {
        return transportationServiceRepository.findById(transportationServiceId)
                .map(transportationServiceMapper::toTransportationServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + transportationServiceId));
    }

    public List<TransportationServiceResponse> findAll() {
        return transportationServiceRepository.findAll().stream()
                .map(transportationServiceMapper::toTransportationServiceResponse)
                .collect(Collectors.toList());
    }
}
