package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportationService extends Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TransportType transportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String company;

    @OneToOne
    private Location origin;
}
