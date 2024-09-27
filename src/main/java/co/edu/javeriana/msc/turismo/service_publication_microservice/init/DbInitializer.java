    package co.edu.javeriana.msc.turismo.service_publication_microservice.init;

    import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.SuperServiceDTO;
    import co.edu.javeriana.msc.turismo.service_publication_microservice.enums.CRUDEventType;
    import co.edu.javeriana.msc.turismo.service_publication_microservice.mapper.SuperServiceMapper;
    import co.edu.javeriana.msc.turismo.service_publication_microservice.model.*;
    import co.edu.javeriana.msc.turismo.service_publication_microservice.queue.ServicesQueueService;
    import co.edu.javeriana.msc.turismo.service_publication_microservice.repository.*;
    import lombok.extern.slf4j.Slf4j;
    import net.datafaker.Faker;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.stereotype.Component;

    import java.math.BigDecimal;
    import java.time.Duration;
    import java.time.Instant;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.Arrays;
    import java.util.List;

    @Component
    @Slf4j
    public class DbInitializer implements CommandLineRunner {

        @Autowired
        private ServiceRepository serviceRepository;

        @Autowired
        private AccommodationServiceRepository accommodationServiceRepository;

        @Autowired
        private AccommodationTypeRepository accommodationTypeRepository;

        @Autowired
        private FoodServiceRepository foodServiceRepository;

        @Autowired
        private FoodTypeRepository foodTypeRepository;

        @Autowired
        private TransportationServiceRepository transportationServiceRepository;

        @Autowired
        private TransportTypeRepository transportTypeRepository;

        @Autowired
        private LocationRepository locationRepository;

        @Autowired
        private ServicesQueueService servicesQueueService;

        @Autowired
        private SuperServiceMapper superServiceMapper;

        @Override
        public void run(String... args) throws Exception {

            Faker fakerGeneral = new Faker();
            Instant now = Instant.now();



            // Asegúrate de que startDate es una fecha válida dentro del rango
            Instant startDate = fakerGeneral.timeAndDate().between(now, now.plus(Duration.ofDays(30)));
            Instant endDate = fakerGeneral.timeAndDate().between(startDate.plus(Duration.ofDays(2)), startDate.plus(Duration.ofDays(32)));

            List<String> accommodationTypes = Arrays.asList(
                    "HOTEL", "HOSTEL", "APARTMENT", "CAMPING", "RESORT",
                    "MOTEL", "GUEST_HOUSE", "BED_AND_BREAKFAST", "VILLA",
                    "FARM_STAY", "COUNTRY_HOUSE", "LODGE", "HOLIDAY_HOME",
                    "RESIDENCE", "APARTHOTEL", "INN", "BOUTIQUE_HOTEL",
                    "PENSION", "COTTAGE", "CHALET"
            );

            accommodationTypes.forEach(type -> {
                AccommodationType accommodationType = new AccommodationType(type);
                accommodationTypeRepository.save(accommodationType);
            });

            List<String> foodTypes = Arrays.asList("BREAKFAST", "LUNCH", "DINNER", "SNACK", "DRINKS", "DESSERT");

            foodTypes.forEach(type -> {
                FoodType foodType = new FoodType(type);
                foodTypeRepository.save(foodType);
            });

            List<String> transportTypes = Arrays.asList(
                    "AIRPLANE", "BUS", "TRAIN", "CAR", "BOAT", "BICYCLE",
                    "MOTORCYCLE", "WALKING", "HORSE", "HELICOPTER",
                    "SUBWAY", "TAXI", "TRAM", "CABLE_CAR", "FERRY",
                    "CRUISE", "JEEP", "MINIVAN", "VAN", "TRUCK"
            );

            transportTypes.forEach(type -> {
                TransportType transportType = new TransportType(type);
                transportTypeRepository.save(transportType);
            });

            for (int i = 0; i < 10; i++) { //TODO use API to get real locations
                Location location = new Location();
                Faker faker = new Faker();
                //System.out.println(faker.hobby().activity());
                location.setAddress(faker.address().fullAddress());
                location.setCity(faker.address().city());
                location.setCountry(faker.address().country());
                location.setLatitude(Double.parseDouble(faker.address().latitude()));
                location.setLongitude(Double.parseDouble(faker.address().longitude()));
                location.setMunicipality(faker.address().state());
                locationRepository.save(location);
            }

            AccommodationService accommodationService1 = new AccommodationService();
            accommodationService1.setName("Hotel la vida sabrosa");
            accommodationService1.setDescription("Hotel de lujo en la playa, acompañado de un excelente servicio y comida y nada mejor que el mar a tus pies.");
            accommodationService1.setType(accommodationTypeRepository.findByName("HOTEL"));
            accommodationService1.setStartDate(startDate);
            accommodationService1.setEndDate(endDate);
            accommodationService1.setCapacity(fakerGeneral.number().numberBetween(1, 10));
            accommodationService1.setUnitValue(BigDecimal.valueOf(fakerGeneral.number().numberBetween(100000, 1000000)));
            accommodationService1.setDestination(locationRepository.findById(1L).get());
            accommodationService1.setCreatedBy("524f9a80-156e-4ccb-b0ea-474dcb8664e7");

            var accommodation = accommodationServiceRepository.save(accommodationService1);
            var superService = superServiceMapper.toSuperService(accommodation);
            servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, superService));
            // Para FoodService
            Instant foodServiceStartDate = fakerGeneral.timeAndDate().between(now, now.plus(Duration.ofDays(30)));
            Instant foodServiceEndDate = fakerGeneral.timeAndDate().between(foodServiceStartDate.plus(Duration.ofHours(1)), foodServiceStartDate.plus(Duration.ofHours(5))); // Asegúrate de que foodServiceEndDate sea después de foodServiceStartDate

            FoodService foodService1 = new FoodService();
            foodService1.setName("Restaurante así es la vida");
            foodService1.setDescription("Restaurante de lujo en las montañas, acompañado de un excelente servicio y comida y nada mejor que el aire fresco de la montaña.");
            foodService1.setFoodType(foodTypeRepository.findByName("LUNCH"));
            foodService1.setUnitValue(BigDecimal.valueOf(fakerGeneral.number().numberBetween(10000, 100000)));
            foodService1.setStartDate(foodServiceStartDate);
            foodService1.setEndDate(foodServiceEndDate);
            foodService1.setDestination(locationRepository.findById(2L).get());
            foodService1.setCreatedBy("524f9a80-156e-4ccb-b0ea-474dcb8664e7");

            var food = foodServiceRepository.save(foodService1);
            var foodSuperService = superServiceMapper.toSuperService(food);
            servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, foodSuperService));

            // Para TransportationService
            Instant transportServiceStartDate = fakerGeneral.timeAndDate().between(now, now.plus(Duration.ofDays(30)));
            Instant transportServiceEndDate = fakerGeneral.timeAndDate().between(transportServiceStartDate.plus(Duration.ofHours(1)), transportServiceStartDate.plus(Duration.ofHours(19))); // Asegúrate de que transportServiceEndDate sea después de transportServiceStartDate

            TransportationService transportationService1 = new TransportationService();
            transportationService1.setName("Viaje en avión");
            transportationService1.setDescription("Viaje en avión de lujo, acompañado de un excelente servicio de meriendas y nada mejor que la vista en primera clase de las nubes.");
            transportationService1.setTransportType(transportTypeRepository.findByName("AIRPLANE"));
            transportationService1.setStartDate(transportServiceStartDate);
            transportationService1.setEndDate(transportServiceEndDate);
            transportationService1.setCompany("Avianca");
            transportationService1.setUnitValue(BigDecimal.valueOf(fakerGeneral.number().numberBetween(100000, 1000000)));
            transportationService1.setOrigin(locationRepository.findById(4L).get());
            transportationService1.setDestination(locationRepository.findById(3L).get());
            transportationService1.setCreatedBy("524f9a80-156e-4ccb-b0ea-474dcb8664e7");

            var transportation = transportationServiceRepository.save(transportationService1);
            var transportSuperService = superServiceMapper.toSuperService(transportation);
            servicesQueueService.sendServices(new SuperServiceDTO(LocalDateTime.now(), CRUDEventType.CREATE, transportSuperService));
            log.info("Sending services to queue: {}", transportSuperService);


        }
    }
