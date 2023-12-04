package com.gavrilo.cityweather.city;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityWeatherDTOTest {

    @Test
    void cityWeatherDTOConstructor() {
        City city = City.builder()
                .id(1)
                .name("TestCity")
                .country("TestCountry")
                .stateRegion("TestState")
                .population(100000)
                .build();

        int temperature = 25;
        CityWeatherDTO cityWeatherDTO = new CityWeatherDTO(city, temperature);

        assertNotNull(cityWeatherDTO);
        assertEquals(1, cityWeatherDTO.getId());
        assertEquals("TestCity", cityWeatherDTO.getName());
        assertEquals("TestCountry", cityWeatherDTO.getCountry());
        assertEquals("TestState", cityWeatherDTO.getStateRegion());
        assertEquals(100000, cityWeatherDTO.getPopulation());
        assertEquals(25, cityWeatherDTO.getTemp_c());
    }

    @Test
    void cityWeatherDTOEquality() {
        City city1 = City.builder()
                .id(1)
                .name("TestCity")
                .country("TestCountry")
                .stateRegion("TestState")
                .population(100000)
                .build();

        City city2 = City.builder()
                .id(1)
                .name("TestCity")
                .country("TestCountry")
                .stateRegion("TestState")
                .population(100000)
                .build();

        int temperature = 25;
        CityWeatherDTO dto1 = new CityWeatherDTO(city1, temperature);
        CityWeatherDTO dto2 = new CityWeatherDTO(city2, temperature);

        // Test equality
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Change a field
        dto2.setTemp_c(30);

        // Test inequality
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}