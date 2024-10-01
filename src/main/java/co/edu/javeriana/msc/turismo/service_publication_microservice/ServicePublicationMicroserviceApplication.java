package co.edu.javeriana.msc.turismo.service_publication_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicePublicationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePublicationMicroserviceApplication.class, args);
	}

}
