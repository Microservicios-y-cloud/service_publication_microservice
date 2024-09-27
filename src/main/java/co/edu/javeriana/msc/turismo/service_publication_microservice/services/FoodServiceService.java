package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodServiceRepository;
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
public class FoodServiceService {

    private final FoodServiceRepository foodServiceRepository;
    private final FoodServiceMapper foodServiceMapper;
    private final SuperServiceMapper superServiceMapper;
    private final ServicesQueueService servicesQueueService;
    public Long createService(FoodServiceRequest request) {
        var service = foodServiceMapper.toFoodService(request);
        FoodService foodService = foodServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(foodService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Food service sent to queue: {}", superService);
        return foodService.getId();
    }

    public FoodServiceResponse findById(Long foodServiceId) {
        return foodServiceRepository.findById(foodServiceId)
                .map(foodServiceMapper::toFoodServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + foodServiceId));
    }

    public List<FoodServiceResponse> findAll() {
        return foodServiceRepository.findAll().stream()
                .map(foodServiceMapper::toFoodServiceResponse)
                .collect(Collectors.toList());
    }
}
