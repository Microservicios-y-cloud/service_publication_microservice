package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
