package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import org.springframework.stereotype.Service;

@Service
public class AccommodationTypeMapper {

    // Convertir de FoodType a FoodTypeResponse
    public AccommodationTypeResponse toaccommodationTypeResponse(AccommodationType foodType) {
        if (foodType == null) {
            return null;
        }

        return new AccommodationTypeResponse(
                foodType.getId(),       // Usa getId() aquí
                foodType.getName()      // Usa getName() aquí
        );
    }

    // Convertir de FoodTypeResponse a FoodType
    public AccommodationType toaccommodationType(AccommodationTypeResponse accommodationTypeResponse) {
        if (accommodationTypeResponse == null) {
            return null;
        }

        return AccommodationType.builder()
                .id(accommodationTypeResponse.accommodationTypeId())   // Usa FoodTypeId() aquí
                .name(accommodationTypeResponse.name())       // Usa name() aquí
                .build();
    }
}
