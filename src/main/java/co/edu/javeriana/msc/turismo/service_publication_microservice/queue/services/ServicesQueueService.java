package co.edu.javeriana.msc.turismo.service_publication_microservice.queue.services;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.LocationDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto.SuperServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ServicesQueueService {
    // This attribute will store the queue name,
    // which is obtained from
    // the property spring.cloud.stream.bindings.sendMessage-out-0.destination
    // from the application.yml file
    @Value("${spring.cloud.stream.bindings.sendServices-out-0.destination}")
    private String servicesQueue;

    @Value("${spring.cloud.stream.bindings.sendServiceTypes-out-0.destination}")
    private String serviceType;

    @Value("${spring.cloud.stream.bindings.sendLocation-out-0.destination}")
    private String locationQueue;

    @Autowired
    private StreamBridge streamBridge;
    public boolean sendServices(SuperServiceDTO message) {
        return streamBridge.send(servicesQueue, MessageBuilder.withPayload(message).build());
    }
    public boolean sendServiceType(ServiceTypeDTO message) {
        return streamBridge.send(serviceType, MessageBuilder.withPayload(message).build());
    }
    public boolean sendLocation(LocationDTO message) {
        return streamBridge.send(locationQueue, MessageBuilder.withPayload(message).build());
    }

}
