package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.ServiceType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SuperServiceMapper {
    public SuperService toSuperService(AccommodationService service) {
        return new SuperService(
                service.getId(),
                service.getCreatedBy(),
                new LocationResponse(
                        service.getDestination().getId(),
                        service.getDestination().getAddress(),
                        service.getDestination().getLatitude(),
                        service.getDestination().getLongitude(),
                        service.getDestination().getCountry(),
                        service.getDestination().getCity(),
                        service.getDestination().getMunicipality()
                ),
                service.getName(),
                service.getDescription(),
                service.getUnitValue(),
                LocalDateTime.ofInstant(service.getStartDate(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(service.getEndDate(), ZoneId.systemDefault()),
                ServiceType.ACCOMMODATION,
                null,
                new AccommodationTypeResponse(
                        service.getType().getId(),
                        service.getType().getName()
                ),
                service.getCapacity(),
                null,
                null,
                null
        );
    }

    public SuperService toSuperService(FoodService service) {
        return new SuperService(
                service.getId(),
                service.getCreatedBy(),
                new LocationResponse(
                        service.getDestination().getId(),
                        service.getDestination().getAddress(),
                        service.getDestination().getLatitude(),
                        service.getDestination().getLongitude(),
                        service.getDestination().getCountry(),
                        service.getDestination().getCity(),
                        service.getDestination().getMunicipality()
                ),
                service.getName(),
                service.getDescription(),
                service.getUnitValue(),
                LocalDateTime.ofInstant(service.getStartDate(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(service.getEndDate(), ZoneId.systemDefault()),
                ServiceType.FOOD,
                new FoodTypeResponse(
                        service.getFoodType().getId(),
                        service.getFoodType().getName()
                ),
                null,
                null,
                null,
                null,
                null
        );
    }

    public SuperService toSuperService(TransportationService service) {
        return new SuperService(
                service.getId(),
                service.getCreatedBy(),
                new LocationResponse(
                        service.getDestination().getId(),
                        service.getDestination().getAddress(),
                        service.getDestination().getLatitude(),
                        service.getDestination().getLongitude(),
                        service.getDestination().getCountry(),
                        service.getDestination().getCity(),
                        service.getDestination().getMunicipality()
                ),
                service.getName(),
                service.getDescription(),
                service.getUnitValue(),
                LocalDateTime.ofInstant(service.getStartDate(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(service.getEndDate(), ZoneId.systemDefault()),
                ServiceType.TRANSPORTATION,
                null,
                null,
                null,
                new TransportTypeResponse(
                        service.getTransportType().getId(),
                        service.getTransportType().getName()
                ),
                new LocationResponse(
                        service.getOrigin().getId(),
                        service.getOrigin().getAddress(),
                        service.getOrigin().getLatitude(),
                        service.getOrigin().getLongitude(),
                        service.getOrigin().getCountry(),
                        service.getOrigin().getCity(),
                        service.getOrigin().getMunicipality()
                ),
                service.getCompany()
        );
    }
}
