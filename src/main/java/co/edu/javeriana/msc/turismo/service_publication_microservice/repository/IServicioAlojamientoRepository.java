package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.ServicioAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioAlojamientoRepository extends JpaRepository<ServicioAlojamiento, Long> {
}
