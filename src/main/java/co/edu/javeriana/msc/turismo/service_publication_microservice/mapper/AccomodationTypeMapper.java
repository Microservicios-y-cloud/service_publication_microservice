package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import org.springframework.stereotype.Service;

@Service
public class AccomodationTypeMapper {

    // Convertir de FoodType a FoodTypeResponse
    public AccommodationTypeResponse toAccomodationTypeResponse(AccommodationType foodType) {
        if (foodType == null) {
            return null;
        }

        return new AccommodationTypeResponse(
                foodType.getId(),       // Usa getId() aquí
                foodType.getName()      // Usa getName() aquí
        );
    }

    // Convertir de FoodTypeResponse a FoodType
    public AccommodationType toAccomodationType(AccommodationTypeResponse accommodationTypeResponse) {
        if (accommodationTypeResponse == null) {
            return null;
        }

        return AccommodationType.builder()
                .id(accommodationTypeResponse.accomodationTypeId())   // Usa FoodTypeId() aquí
                .name(accommodationTypeResponse.name())       // Usa name() aquí
                .build();
    }
}
