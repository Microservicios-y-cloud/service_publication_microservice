package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransportationService extends Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "transportation_type_id", nullable = false)
    private TransportType transportType; // Referencia a la entidad TransportationType
    private String company;

    @OneToOne
    @JoinColumn(name = "origin_id") // La columna origin_id será la clave externa que referencia la tabla Location
    private Location origin;
}
