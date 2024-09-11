package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TransportationServiceResponse(
        ServiceResponse serviceResponse,
        Long originId,
        String originCountry,
        String originCity,
        Long transportTypeId,
        String transportType,
        String company
) {
}
