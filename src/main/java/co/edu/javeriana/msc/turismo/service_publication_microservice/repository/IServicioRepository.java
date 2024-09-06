package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Servicio;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.ServicioAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServicioRepository extends JpaRepository<Servicio, Long> {
}
