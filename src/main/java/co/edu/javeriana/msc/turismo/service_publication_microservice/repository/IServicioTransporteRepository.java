package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.ServicioTransporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioTransporteRepository extends JpaRepository<ServicioTransporte, Long> {
}
