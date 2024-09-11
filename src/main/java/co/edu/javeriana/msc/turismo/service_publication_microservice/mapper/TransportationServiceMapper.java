package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import org.springframework.stereotype.Service;

@Service
public class TransportationServiceMapper {
    public TransportationService toTransportationService(TransportationServiceRequest request) {
        return TransportationService.builder()
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
                .transportType(
                        TransportType.builder()
                                .id(request.transportTypeId())
                                .build())
                .company(request.company())
                .origin(
                        Location.builder()
                                .id(request.originId())
                                .build()
                )
                .build();
    }

    public TransportationServiceResponse toTransportationServiceResponse(TransportationService transportationService) {
        return new TransportationServiceResponse(
                new ServiceResponse(
                        transportationService.getId(),
                        transportationService.getName(),
                        transportationService.getDescription(),
                        transportationService.getUnitValue(),
                        transportationService.getDestination().getId(),
                        transportationService.getDestination().getCountry(),
                        transportationService.getDestination().getCity(),
                        transportationService.getStartDate(),
                        transportationService.getEndDate()
                ),
                transportationService.getOrigin().getId(),
                transportationService.getOrigin().getCountry(),
                transportationService.getOrigin().getCity(),
                transportationService.getTransportType().getId(),
                transportationService.getTransportType().getName(),
                transportationService.getCompany()
        );
    }
}
