package co.edu.javeriana.msc.turismo.service_publication_microservice.service;

import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioAlimentacionRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioAlimentacionService {

    @Autowired
    private IServicioAlimentacionRepository servicioAlimentacionRepository;
}
