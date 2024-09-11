package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name; // Nombre del tipo de comida, e.g., "VEGETARIAN", "NON_VEGETARIAN", etc.

    public AccommodationType(String nombre) {
        this.name = nombre;
    }
}
