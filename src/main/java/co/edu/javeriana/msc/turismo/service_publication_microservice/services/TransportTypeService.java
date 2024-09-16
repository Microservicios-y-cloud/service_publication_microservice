package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.FoodTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.TransportTypeMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportTypeRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportTypeService {
    private final TransportTypeRepository TransportTypeRepository;
    private final TransportTypeMapper TransportTypeMapper;

    public TransportTypeResponse findById(Long foodTypeId) {
        return TransportTypeRepository.findById(foodTypeId)
                .map(TransportTypeMapper::toTransportTypeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Food type not found with id: " + foodTypeId));
    }

    public List<TransportTypeResponse> findAll() {
        return TransportTypeRepository.findAll().stream()
                .map(TransportTypeMapper::toTransportTypeResponse)
                .collect(Collectors.toList());
    }
}
