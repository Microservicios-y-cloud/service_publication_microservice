package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services.ServicesQueueService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodTypeMapper foodTypeMapper;
    private final ServiceTypeMapper serviceTypeMapper;
    private final ServicesQueueService servicesQueueService;

    public Long createFoodType(FoodTypeResponse request) {
        var foodType = foodTypeMapper.toFoodType(request);
        FoodType foodTypeEntity = foodTypeRepository.save(foodType);
        var serviceType = serviceTypeMapper.toServiceType(foodTypeEntity);
        var sent = servicesQueueService.sendServiceType(new ServiceTypeDTO(LocalDateTime.now(), CRUDEventType.CREATE, serviceType));

        if(sent) {
            log.info("Food type sent to queue to {}: {}", CRUDEventType.CREATE, serviceType);
        } else {
            log.error("Error sending food type to queue to {}: {}", CRUDEventType.CREATE, serviceType);
            throw new ServiceUnavailableException("Error sending food type to queue. Kafka service is currently unavailable. Please try again later.");
        }

        return foodTypeEntity.getId();
    }

}
