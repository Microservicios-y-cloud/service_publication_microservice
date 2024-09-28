package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceType;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeMapper {
    public ServiceType toServiceType(AccommodationType accommodationDB) {
        return new ServiceType(
                accommodationDB.getId(),
                accommodationDB.getName(),
                co.edu.javeriana.msc.turismo.service_publication_microservice.enums.ServiceType.ACCOMMODATION
        );
    }
    public ServiceType toServiceType(FoodType foodDB) {
        return new ServiceType(
                foodDB.getId(),
                foodDB.getName(),
                co.edu.javeriana.msc.turismo.service_publication_microservice.enums.ServiceType.FOOD
        );
    }
    public ServiceType toServiceType(TransportType transportDB) {
        return new ServiceType(
                transportDB.getId(),
                transportDB.getName(),
                co.edu.javeriana.msc.turismo.service_publication_microservice.enums.ServiceType.TRANSPORTATION
        );
    }
}
