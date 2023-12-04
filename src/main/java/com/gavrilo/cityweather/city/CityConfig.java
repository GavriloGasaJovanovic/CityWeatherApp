package com.gavrilo.cityweather.city;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class to seed initial data to the CityRepository.
 */
@Configuration
public class CityConfig {
    @Bean
    CommandLineRunner commandLineRunner(CityRepository repository) {
        return args -> {
            City vienna = new City(
                    1,
                    "Vienna",
                    "Austria",
                    "Vienna",
                    1897000
            );

            City belgrade = new City(
                    2,
                    "Belgrade",
                    "Serbia",
                    "Belgrade",
                    2000000
            );

            repository.saveAll(
                    List.of(vienna, belgrade)
            );
        };
    }
}
