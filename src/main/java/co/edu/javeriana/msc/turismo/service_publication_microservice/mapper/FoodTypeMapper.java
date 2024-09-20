package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import org.springframework.stereotype.Service;

@Service
public class FoodTypeMapper {

    // Convertir de FoodType a FoodTypeResponse
    public FoodTypeResponse toFoodTypeResponse(FoodType foodType) {
        if (foodType == null) {
            return null;
        }

        return new FoodTypeResponse(
                foodType.getId(),       // Usa getId() aquí
                foodType.getName()      // Usa getName() aquí
        );
    }

    // Convertir de FoodTypeResponse a FoodType
    public FoodType toFoodType(FoodTypeResponse foodTypeResponse) {
        if (foodTypeResponse == null) {
            return null;
        }

        return FoodType.builder()
                .id(foodTypeResponse.foodTypeId())   // Usa FoodTypeId() aquí
                .name(foodTypeResponse.name())       // Usa name() aquí
                .build();
    }
}
