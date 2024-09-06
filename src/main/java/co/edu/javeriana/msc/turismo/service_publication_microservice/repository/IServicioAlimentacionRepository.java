package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.ServicioAlimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioAlimentacionRepository extends JpaRepository<ServicioAlimentacion, Long> {
}
