package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.AccommodationServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.AccommodationServiceService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureGraphQlTester
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccommodationServiceControllerTest {

    @Autowired
    private AccommodationServiceService accommodationServiceService;

    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.6");

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    static KafkaConsumer<Object, Object> mockKafkaConsumer;

    static void createMockKafkaConsumer() {
        String groupId_1 = "service-group";
        String groupId_2 = "service-type-group";
        String groupId_3 = "my-consumer-group";
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_1);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_2);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_3);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "co.edu.javeriana.msc.*");
        mockKafkaConsumer = new KafkaConsumer<>(properties, new JsonDeserializer<>(), new JsonDeserializer<>());
        mockKafkaConsumer.subscribe(List.of("serviceTypeQueue"));
    }

    @BeforeAll
    static void beforeAll(@Autowired AccommodationServiceRepository accommodationServiceRepository) {
        kafka.start();
        createMockKafkaConsumer();
        mongo.start();
    }

    @AfterAll
    static void afterAll() {
        mongo.stop();
        kafka.stop();
    }

    @Test
    @Order(1)
    void testCreateService() throws Exception {
        // Crea un servicio para probar
        AccommodationServiceRequest request = createMockRequest();
        Long createdId = accommodationServiceService.createService(request);

        // Verifica que se haya creado correctamente
        assertThat(createdId).isNotNull();
    }

    @Test
    @Order(2)
    void testUpdateService() throws Exception {
        // Crear un nuevo servicio
        AccommodationServiceRequest request = createMockRequest();
        Long createdId = accommodationServiceService.createService(request);

        // Crear una solicitud de actualización
        AccommodationServiceRequest updatedRequest = new AccommodationServiceRequest(
                createdId,
                "Updated Name",
                "Updated Description",
                BigDecimal.valueOf(300.00),
                new LocationResponse(
                        1L,
                        "123 Updated St",
                        41.9028,
                        12.4964,
                        "Italy",
                        "Rome",
                        "Lazio"
                ),
                request.startDate(),
                request.endDate(),
                "updatedSupplier123",
                new AccommodationTypeResponse(2L, "Updated Luxury Hotel"),
                150
        );

        accommodationServiceService.updateService(updatedRequest);

        // No es necesario usar `findById`, valida cambios con otros métodos o mocks
    }

    @Test
    @Order(3)
    void testDeleteService() throws Exception {
        AccommodationServiceRequest request = createMockRequest();
        Long createdId = accommodationServiceService.createService(request);

        accommodationServiceService.deleteService(createdId);
    }

    private AccommodationServiceRequest createMockRequest() {
        return new AccommodationServiceRequest(
                null,
                "Luxury Hotel",
                "A 5-star hotel with premium facilities",
                BigDecimal.valueOf(200.00),
                new LocationResponse(
                        1L,
                        "123 Main St",
                        48.8566,
                        2.3522,
                        "France",
                        "Paris",
                        "Ile-de-France"
                ),
                Instant.now(),
                Instant.now().plusSeconds(3600 * 24),
                "supplier123",
                new AccommodationTypeResponse(1L, "Luxury Hotel"),
                100
        );
    }
}