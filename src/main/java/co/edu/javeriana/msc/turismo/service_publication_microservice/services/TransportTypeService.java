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
    private final TransportTypeRepository transportTypeRepository;
    private final TransportTypeMapper transportTypeMapper;

    public TransportTypeResponse findById(Long foodTypeId) {
        return transportTypeRepository.findById(foodTypeId)
                .map(transportTypeMapper::toTransportTypeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Food type not found with id: " + foodTypeId));
    }

    public List<TransportTypeResponse> findAll() {
        return transportTypeRepository.findAll().stream()
                .map(transportTypeMapper::toTransportTypeResponse)
                .collect(Collectors.toList());
    }
}
