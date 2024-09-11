package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}

