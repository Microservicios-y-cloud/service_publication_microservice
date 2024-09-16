package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import org.springframework.stereotype.Service;

@Service
public class TransportTypeMapper {

    // Convertir de FoodType a FoodTypeResponse
    public TransportTypeResponse toTransportTypeResponse(TransportType foodType) {
        if (foodType == null) {
            return null;
        }

        return new TransportTypeResponse(
                foodType.getId(),       // Usa getId() aquí
                foodType.getName()      // Usa getName() aquí
        );
    }

    // Convertir de FoodTypeResponse a FoodType
    public TransportType toTransportType(TransportTypeResponse foodTypeResponse) {
        if (foodTypeResponse == null) {
            return null;
        }

        return TransportType.builder()
                .id(foodTypeResponse.AccomodationTypeId())   // Usa FoodTypeId() aquí
                .name(foodTypeResponse.name())       // Usa name() aquí
                .build();
    }
}
