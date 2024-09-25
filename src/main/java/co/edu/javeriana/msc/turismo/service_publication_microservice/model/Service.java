package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitValue;

    @ManyToOne
    private Location destination;

    private Instant startDate;
    private Instant  endDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

}
