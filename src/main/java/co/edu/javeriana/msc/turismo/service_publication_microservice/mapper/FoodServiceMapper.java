package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceMapper {
    public FoodService toFoodService(FoodServiceRequest request) {
        return FoodService.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .unitValue(request.unitValue())
                .destination(
                        Location.builder()
                                .id(request.destinationId())
                                .build())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdBy(request.supplierId())
                .foodType(
                        FoodType.builder()
                                .id(request.foodTypeId())
                                .build())
                .build();
    }

    public FoodServiceResponse toFoodServiceResponse(FoodService foodService) {
        return new FoodServiceResponse(
                new ServiceResponse(
                        foodService.getId(),
                        foodService.getName(),
                        foodService.getDescription(),
                        foodService.getUnitValue(),
                        foodService.getDestination().getId(),
                        foodService.getDestination().getCountry(),
                        foodService.getDestination().getCity(),
                        foodService.getStartDate(),
                        foodService.getEndDate(),
                        foodService.getCreatedBy()
                ),
                foodService.getFoodType().getId(),
                foodService.getFoodType().getName()
        );
    }
}
