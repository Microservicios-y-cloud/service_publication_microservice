package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.TransportationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.TransportationServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportationServiceService {
    private final TransportationServiceRepository transportationServiceRepository;
    private final TransportationServiceMapper transportationServiceMapper;
    public Long createService(TransportationServiceRequest request) {
        var service = transportationServiceMapper.toTransportationService(request);
        return transportationServiceRepository.save(service).getId();
    }

    public TransportationServiceResponse findById(Long transportationServiceId) {
        return transportationServiceRepository.findById(transportationServiceId)
                .map(transportationServiceMapper::toTransportationServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + transportationServiceId));
    }

    public List<TransportationServiceResponse> findAll() {
        return transportationServiceRepository.findAll().stream()
                .map(transportationServiceMapper::toTransportationServiceResponse)
                .collect(Collectors.toList());
    }
}
