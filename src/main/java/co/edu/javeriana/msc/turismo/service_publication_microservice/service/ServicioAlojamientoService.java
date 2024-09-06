package co.edu.javeriana.msc.turismo.service_publication_microservice.service;

import co.edu.javeriana.msc.turismo.service_publication_microservice.model.ServicioAlojamiento;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioAlojamientoRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAlojamientoService {

    @Autowired
    private IServicioAlojamientoRepository servicioAlojamientoRepository;
//    List<ServicioAlojamiento> findByAnyContaining
}
