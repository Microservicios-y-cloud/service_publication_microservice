package co.edu.javeriana.msc.turismo.service_publication_microservice;

import me.yaman.can.webflux.h2console.H2ConsoleAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(value={H2ConsoleAutoConfiguration.class})
public class ServicePublicationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePublicationMicroserviceApplication.class, args);
	}

}
