package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceResponse findById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .map(serviceMapper::toServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Service not found, with id: " + serviceId));
    }

    public List<ServiceResponse> findAll() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }
}
