package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;

@org.springframework.stereotype.Service
public class LocationMapper {
    public LocationResponse toLocationResponse(Location location) {
        return new LocationResponse(
            location.getId(),  // Añadido el id
            location.getAddress(),
            location.getLatitude(),
            location.getLongitude(),
            location.getCountry(),
            location.getCity(),
            location.getMunicipality()
        );
    }

    public Location toLocation(LocationResponse request) {
        return new Location(
            request.id(),
            request.address(),
            request.latitude(),
            request.longitude(),
            request.country(),
            request.city(),
            request.municipality()
        );
    }
}
