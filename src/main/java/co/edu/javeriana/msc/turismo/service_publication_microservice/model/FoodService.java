package co.edu.javeriana.msc.turismo.service_publication_microservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodService extends Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "food_type_id", nullable = false)
    private FoodType foodType; // Referencia a la entidad FoodType
}
