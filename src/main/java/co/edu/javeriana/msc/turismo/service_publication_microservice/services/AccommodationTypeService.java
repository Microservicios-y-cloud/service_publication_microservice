package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccomodationTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationTypeRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final AccomodationTypeMapper accommodationTypeMapper;

}
