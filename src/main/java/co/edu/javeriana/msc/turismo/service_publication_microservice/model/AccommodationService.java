package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationService extends Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "accommodation_type_id", nullable = false)
    private AccommodationType type; // Referencia a la entidad AccommodationType
    private Integer capacity;
}
