package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
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
public class FoodServiceService {

    private final FoodServiceRepository foodServiceRepository;
    private final FoodServiceMapper foodServiceMapper;
    private final SuperServiceMapper superServiceMapper;
    private final ServicesQueueService servicesQueueService;
    private final FoodTypeRepository foodTypeRepository;
    private final LocationRepository locationRepository;
    public Long createService(FoodServiceRequest request) {
        var service = foodServiceMapper.toFoodService(request);
        if (!locationRepository.existsById(service.getDestination().getId())) {
            throw new EntityNotFoundException("Location not found");
        }
        if(!foodTypeRepository.existsById(service.getFoodType().getId())) {
            throw new EntityNotFoundException("Food type not found");
        }
        FoodService foodService = foodServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(foodService);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        log.info("Food service sent to queue to {}: {}", CRUDEventType.CREATE, superService);
        return foodService.getId();
    }

    public void deleteService(Long id) {
        var service = foodServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.DELETE, superService));
        log.info("Food service sent to queue to {}: {}", CRUDEventType.DELETE, superService);
        foodServiceRepository.deleteById(id);
    }
    public void updateService(@Valid FoodServiceRequest request) {
        var foodService = foodServiceRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        mergerService(foodService, request);
        var service = foodServiceRepository.save(foodService);
        var superService = superServiceMapper.toSuperService(service);
        servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.UPDATE, superService));
        log.info("Food service sent to queue to {}: {}", CRUDEventType.UPDATE, superService);
    }

    private void mergerService(FoodService foodService, @Valid FoodServiceRequest request) {
        if (request.destination() != null) {
            foodService.setDestination(locationRepository.findById(request.destination().id()).orElseThrow(() -> new EntityNotFoundException("Location not found")));
        }
        if (request.foodType() != null) {
            foodService.setFoodType(foodTypeRepository.findById(request.foodType().foodTypeId()).orElseThrow(() -> new EntityNotFoundException("Food type not found")));
        }
        if(StringUtils.isNotBlank(request.name())){
            foodService.setName(request.name());
        }
        if(StringUtils.isNotBlank(request.supplierId())){
            foodService.setCreatedBy(request.supplierId());
        }
        if(request.startDate() != null){
            foodService.setStartDate(request.startDate());
        }
        if(request.endDate() != null){
            foodService.setEndDate(request.endDate());
        }
        if(request.unitValue() != null){
            foodService.setUnitValue(request.unitValue());
        }
        foodService.setDescription(request.description());
    }
}
