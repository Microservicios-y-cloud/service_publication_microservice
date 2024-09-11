package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
    //find by name
    AccommodationType findByName(String name);
}
