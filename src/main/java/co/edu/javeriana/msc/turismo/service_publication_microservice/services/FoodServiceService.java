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
import jakarta.ws.rs.ServiceUnavailableException;
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
        //acá si toca guardarlo antes de crearlo porque toca enviarle el id a los suscriptores
        FoodService foodService = foodServiceRepository.save(service);
        var superService = superServiceMapper.toSuperService(foodService);
        var sent = servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
        if(sent) {
            log.info("Food service sent to queue to {}: {}", CRUDEventType.CREATE, superService);
        } else {
            log.error("Error sending food service to queue to {}: {}", CRUDEventType.CREATE, superService);
            throw new ServiceUnavailableException("Error sending food service to queue. Kafka service is currently unavailable. Please try again later.");
        }
        return foodService.getId();
    }

    public void deleteService(Long id) {
        var service = foodServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        var superService = superServiceMapper.toSuperService(service);
        var sent = servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.DELETE, superService));
        foodServiceRepository.delete(service);
        if(sent) {
            log.info("Food service sent to queue to {}: {}", CRUDEventType.DELETE, superService);
        } else {
            log.error("Error sending food service to queue to {}: {}", CRUDEventType.DELETE, superService);
            throw new ServiceUnavailableException("Error sending food service to queue. Kafka service is currently unavailable. Please try again later.");
        }
    }
    public void updateService(@Valid FoodServiceRequest request) {
        var foodService = foodServiceRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        mergerService(foodService, request);
        var superService = superServiceMapper.toSuperService(foodService);
        var sent = servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.UPDATE, superService));
        foodServiceRepository.save(foodService);
        if(sent) {
            log.info("Food service sent to queue to {}: {}", CRUDEventType.UPDATE, superService);
        } else {
            log.error("Error sending food service to queue to {}: {}", CRUDEventType.UPDATE, superService);
            throw new ServiceUnavailableException("Error sending food service to queue. Kafka service is currently unavailable. Please try again later.");
        }
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
