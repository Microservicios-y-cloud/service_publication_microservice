package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

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
    private AccommodationType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
}
