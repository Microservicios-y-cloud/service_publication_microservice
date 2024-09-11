package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Service;
@org.springframework.stereotype.Service
public class ServiceMapper {
    public ServiceResponse toServiceResponse(Service service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getUnitValue(),
                service.getDestination().getId(),
                service.getDestination().getCountry(),
                service.getDestination().getCity(),
                service.getStartDate(),
                service.getEndDate()
        );
    }
}
