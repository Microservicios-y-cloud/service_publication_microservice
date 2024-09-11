package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // Aquí puedes añadir métodos personalizados si es necesario
}
