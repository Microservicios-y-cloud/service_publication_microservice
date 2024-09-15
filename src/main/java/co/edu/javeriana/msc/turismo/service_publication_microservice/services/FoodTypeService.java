package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodTypeMapper foodTypeMapper;

    public FoodTypeResponse findById(Long foodTypeId) {
        return foodTypeRepository.findById(foodTypeId)
                .map(foodTypeMapper::toFoodTypeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Food type not found with id: " + foodTypeId));
    }

    public List<FoodTypeResponse> findAll() {
        return foodTypeRepository.findAll().stream()
                .map(foodTypeMapper::toFoodTypeResponse)
                .collect(Collectors.toList());
    }
}
