package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.TransportTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportTypeRepository;
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
public class TransportTypeService {
    private final TransportTypeRepository transportTypeRepository;
    private final TransportTypeMapper transportTypeMapper;
    private final ServiceTypeMapper serviceTypeMapper;
    private final ServicesQueueService servicesQueueService;

    public Long createTransportType(TransportTypeResponse request) {
        var transportType = transportTypeMapper.toTransportType(request);
        TransportType transportTypeEntity = transportTypeRepository.save(transportType);
        var serviceType = serviceTypeMapper.toServiceType(transportTypeEntity);
        servicesQueueService.sendServiceType(new ServiceTypeDTO(LocalDateTime.now(), CRUDEventType.CREATE, serviceType));

        log.info("Transport type created: {}", transportTypeEntity);
        return transportTypeEntity.getId();
    }
}
