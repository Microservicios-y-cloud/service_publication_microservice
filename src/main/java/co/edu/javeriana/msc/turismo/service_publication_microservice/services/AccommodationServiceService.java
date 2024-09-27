package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccommodationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationServiceRepository;
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
public class AccommodationServiceService {

    private final AccommodationServiceRepository accommodationServiceRepository;
    private final AccommodationServiceMapper accommodationServiceMapper;
    private final SuperServiceMapper superServiceMapper;
    private final ServicesQueueService servicesQueueService;
    public Long createService(AccommodationServiceRequest request) {
        var service = accommodationServiceMapper.toAccomodationService(request);
        AccommodationService accommodationService = accommodationServiceRepository.save(service);

        var superService = superServiceMapper.toSuperService(accommodationService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Accomodation service sent to queue: {}", superService);

        return accommodationService.getId();
    }

    public AccommodationServiceResponse findById(Long accommodationServiceId) {
        return accommodationServiceRepository.findById(accommodationServiceId)
                .map(accommodationServiceMapper::toAccommodationServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + accommodationServiceId));
    }

    public List<AccommodationServiceResponse> findAll() {
        return accommodationServiceRepository.findAll().stream()
                .map(accommodationServiceMapper::toAccommodationServiceResponse)
                .collect(Collectors.toList());
    }
}
