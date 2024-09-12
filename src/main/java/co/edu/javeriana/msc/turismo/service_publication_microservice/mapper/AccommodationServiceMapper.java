package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceMapper {
        public AccommodationService toAccomodationService(AccommodationServiceRequest request) {
                return AccommodationService.builder()
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
                                .type(
                                                AccommodationType.builder()
                                                                .id(request.accommodationTypeId())
                                                                .build())
                                .capacity(request.capacity())
                                .build();
        }

        public AccommodationServiceResponse toAccommodationServiceResponse(AccommodationService accommodationService) {
                return new AccommodationServiceResponse(
                                new ServiceResponse(
                                                accommodationService.getId(),
                                                accommodationService.getName(),
                                                accommodationService.getDescription(),
                                                accommodationService.getUnitValue(),
                                                accommodationService.getDestination().getId(),
                                                accommodationService.getDestination().getCountry(),
                                                accommodationService.getDestination().getCity(),
                                                accommodationService.getStartDate(),
                                                accommodationService.getEndDate(),
                                                accommodationService.getCreatedBy()
                                ),
                                accommodationService.getType().getId(),
                                accommodationService.getType().getName(),
                                accommodationService.getCapacity());
        }
}
