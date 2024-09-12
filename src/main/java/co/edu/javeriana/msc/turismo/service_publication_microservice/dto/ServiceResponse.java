package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record ServiceResponse(
        Long id,
        String name,
        String description,
        BigDecimal unitValue,
        Long destinationId,
        String country,
        String city,
        Instant startDate,
        Instant  endDate,
        String createdBy
) {
}
