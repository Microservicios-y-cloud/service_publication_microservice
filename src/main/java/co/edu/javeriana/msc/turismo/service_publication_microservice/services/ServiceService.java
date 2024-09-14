package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.ServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public List<ServiceResponse> findAllByKeyword(String keyword) {
        List<co.edu.javeriana.msc.turismo.service_publication_microservice.model.Service> services = new ArrayList<>();

        // Agregar la lógica para verificar si el keyword es un valor numérico (BigDecimal)
        try {
            BigDecimal unitValue = new BigDecimal(keyword);
            services.addAll(serviceRepository.findAllByUnitValueKeyword(unitValue));
        } catch (NumberFormatException | NullPointerException e) {
            // Si no es numérico o es nulo, simplemente ignoramos esta búsqueda
        }

        services.addAll(serviceRepository.findAllByKeyword(keyword));

        log.info("Services found by keyword: {}", services.size());
        return services.stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> findAllBySupplier(String supplierId) {
        return serviceRepository.findAllByCreatedBy(supplierId).stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }
}
