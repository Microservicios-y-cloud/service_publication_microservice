package co.edu.javeriana.msc.turismo.service_publication_microservice.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccomodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.AccommodationServiceMapper;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationServiceService {

    private final AccommodationServiceRepository accommodationServiceRepository;
    private final AccommodationServiceMapper accommodationServiceMapper;
    public Long createService(AccomodationServiceRequest request) {
        var service = accommodationServiceMapper.toAccomodationService(request);
        return accommodationServiceRepository.save(service).getId();
    }

    public AccommodationServiceResponse findById(Long accomodationServiceId) {
        return accommodationServiceRepository.findById(accomodationServiceId)
                .map(accommodationServiceMapper::toAccommodationServiceResponse)
                .orElseThrow(() -> new EntityNotFoundException("Accomodation service not found, with id: " + accomodationServiceId));
    }

    public List<AccommodationServiceResponse> findAll() {
        return accommodationServiceRepository.findAll().stream()
                .map(accommodationServiceMapper::toAccommodationServiceResponse)
                .collect(Collectors.toList());
    }
}
