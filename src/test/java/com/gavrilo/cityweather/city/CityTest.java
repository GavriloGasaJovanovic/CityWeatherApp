package com.gavrilo.cityweather.city;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CityTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testSaveCity() {
        City city = City.builder()
                .name("TestCity")
                .country("TestCountry")
                .stateRegion("TestState")
                .population(100000)
                .build();

        City savedCity = cityRepository.save(city);

        City retrievedCity = cityRepository.findById(savedCity.getId()).orElse(null);

        assertNotNull(retrievedCity);
        assertEquals("TestCity", retrievedCity.getName());
        assertEquals("TestCountry", retrievedCity.getCountry());
        assertEquals("TestState", retrievedCity.getStateRegion());
        assertEquals(100000, retrievedCity.getPopulation());
    }
}
