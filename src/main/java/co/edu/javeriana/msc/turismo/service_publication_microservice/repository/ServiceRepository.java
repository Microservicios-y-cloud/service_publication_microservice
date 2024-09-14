package co.edu.javeriana.msc.turismo.service_publication_microservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT s FROM Service s " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.destination.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.destination.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.destination.country) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.destination.municipality) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Service> findAllByKeyword(String keyword);

    //convertimos el unit value en string para saber si los numeros que tiene unitValue podrían estar en los numeros del unitValue del servicio
    @Query("SELECT s FROM Service s WHERE CAST(s.unitValue AS string) LIKE CONCAT('%', :unitValue, '%')")
    List<Service> findAllByUnitValueKeyword(BigDecimal unitValue);

    List<Service> findAllByCreatedBy(String createdBy);
}

