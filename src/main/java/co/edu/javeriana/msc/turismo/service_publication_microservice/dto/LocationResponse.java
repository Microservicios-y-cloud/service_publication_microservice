package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import jakarta.validation.constraints.NotNull;

public record LocationResponse (
    Long id,
    @NotNull(message = "The address of the service is required")
    String address,
    Double latitude,
    Double longitude,
    @NotNull(message = "The country of the service is required")
    String country,
    @NotNull(message = "The city of the service is required")
    String city,
    @NotNull(message = "The state of the service is required")
    String municipality
) {}
