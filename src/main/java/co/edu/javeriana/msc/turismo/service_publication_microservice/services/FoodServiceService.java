package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccommodationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceService {

    private final FoodServiceRepository foodServiceRepository;
    private final FoodServiceMapper foodServiceMapper;
    public Long createService(FoodServiceRequest request) {
        var service = foodServiceMapper.toFoodService(request);
        return foodServiceRepository.save(service).getId();
    }

    public FoodServiceResponse findById(Long foodServiceId) {
        return foodServiceRepository.findById(foodServiceId)
                .map(foodServiceMapper::toFoodServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + foodServiceId));
    }

    public List<FoodServiceResponse> findAll() {
        return foodServiceRepository.findAll().stream()
                .map(foodServiceMapper::toFoodServiceResponse)
                .collect(Collectors.toList());
    }
}
