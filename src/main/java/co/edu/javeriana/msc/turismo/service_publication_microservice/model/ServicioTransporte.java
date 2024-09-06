package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ServicioTransporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
