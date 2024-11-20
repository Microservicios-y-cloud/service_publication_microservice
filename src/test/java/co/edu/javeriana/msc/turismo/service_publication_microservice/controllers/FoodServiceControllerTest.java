package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.FoodServiceRepository;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.FoodServiceService;
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

@SpringBootTest
@AutoConfigureGraphQlTester
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FoodServiceControllerTest {

    @Autowired
    private FoodServiceService foodServiceService;

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
        String groupId = "food-service-group";
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "co.edu.javeriana.msc.*");
        mockKafkaConsumer = new KafkaConsumer<>(properties, new JsonDeserializer<>(), new JsonDeserializer<>());
        mockKafkaConsumer.subscribe(List.of("foodServiceQueue"));
    }

    @BeforeAll
    static void beforeAll(@Autowired FoodServiceRepository foodServiceRepository) {
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
        FoodServiceRequest request = createMockRequest();
        Long createdId = foodServiceService.createService(request);

        assertThat(createdId).isNotNull();
    }

    @Test
    @Order(2)
    void testUpdateService() throws Exception {
        FoodServiceRequest request = createMockRequest();
        Long createdId = foodServiceService.createService(request);

        FoodServiceRequest updatedRequest = new FoodServiceRequest(
                createdId,
                "Updated Food Service",
                "Updated Description",
                BigDecimal.valueOf(150.00),
                new LocationResponse(
                        1L,
                        "456 Updated St",
                        51.5074,
                        -0.1278,
                        "UK",
                        "London",
                        "England"
                ),
                request.startDate(),
                request.endDate(),
                "updatedSupplier456",
                new FoodTypeResponse(2L, "Updated Gourmet Meal")
        );

        foodServiceService.updateService(updatedRequest);
    }

    @Test
    @Order(3)
    void testDeleteService() throws Exception {
        FoodServiceRequest request = createMockRequest();
        Long createdId = foodServiceService.createService(request);

        foodServiceService.deleteService(createdId);
    }

    private FoodServiceRequest createMockRequest() {
        return new FoodServiceRequest(
                null,
                "Gourmet Meal",
                "A luxury gourmet meal service",
                BigDecimal.valueOf(100.00),
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
                new FoodTypeResponse(1L, "Gourmet Meal")
        );
    }
}
