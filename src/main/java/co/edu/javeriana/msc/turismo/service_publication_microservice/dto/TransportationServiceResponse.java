package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccommodationServiceResponse(
        Long id,
        String name,
        String description,
        BigDecimal unitValue,
        Long destinationId,
        String destinationCountry,
        String destinationCity,
        Instant startDate,
        Instant  endDate,
        Long accommodationTypeId,
        String accommodationType,
        Integer capacity
) {
}
