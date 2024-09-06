package co.edu.javeriana.msc.turismo.service_publication_microservice.service;

import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioTransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioTransporteService {

    @Autowired
    private IServicioTransporteRepository servicioTransporteRepository;
}
