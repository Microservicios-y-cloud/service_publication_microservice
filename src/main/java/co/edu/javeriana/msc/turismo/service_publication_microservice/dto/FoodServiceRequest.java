package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record FoodServiceRequest(
        Long id,
        @NotNull(message = "The name of the service is required")
        String name,
        String description,
        @NotNull(message = "The unit value of the service is required")
        @Positive(message = "The unit value of the service must be positive")
        BigDecimal unitValue,
        @NotNull(message = "The destination of the service is required")
        LocationResponse destination,
        @NotNull(message = "The start date of the service is required")
        Instant startDate,
        @NotNull(message = "The end date of the service is required")
        Instant  endDate,
        @NotNull(message = "The supplier id of the service is required")
        String supplierId,
        @NotNull(message = "The food type of the service is required")
        FoodTypeResponse foodType
) {
}
