package com.gavrilo.cityweather.city;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * CityWeatherDTO
 * Data Transfer Object for providing city information to users.
 */
@Data
public class CityWeatherDTO {
    private Integer id;
    private String name;
    private String country;

    @JsonProperty("state/region")
    private String stateRegion;
    private Integer population;
    private int temp_c;

    /**
     * CityWeatherDTO constructor
     * @param city   The City entity containing basic information.
     * @param tempC  The current temperature in Celsius for the city.
     */
    public CityWeatherDTO(City city, int tempC) {
        this.id = city.getId();
        this.name = city.getName();
        this.country = city.getCountry();
        this.stateRegion = city.getStateRegion();
        this.population = city.getPopulation();
        this.temp_c = tempC;
    }
}