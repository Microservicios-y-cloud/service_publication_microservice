package co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto;

import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccommodationTypeDTO implements Serializable {
    private LocalDateTime dateTime;
    private CRUDEventType eventType;
    private SuperService superService;
}
