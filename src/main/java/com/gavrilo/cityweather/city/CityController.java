package com.gavrilo.cityweather.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling City-related APIs.
 */
@RestController
@RequestMapping(path = "api/v1/city")
public class CityController {
    private final CityService cityService;

    /**
     * CityController constructor
     * @param cityService The CityService to handle city-related operations.
     */
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * API for getting city data by name
     * @param name The name of the city.
     * @return CityWeatherDTO containing information about the city.
     */
    @GetMapping(path = "{name}")
    public CityWeatherDTO getCityByName(@PathVariable("name") String name) {
        return cityService.getCityByName(name);
    }

    /**
     * API for adding a new city
     * @param city The City object to be added.
     */
    @PostMapping
    public void postNewCity(@RequestBody City city) {
        this.cityService.addNewCity(city);
    }

    /**
     * API for deleting a city by its id
     * @param id The id of the city to be deleted.
     */
    @DeleteMapping(path = "{id}")
    public void deleteCity(@PathVariable("id") Integer id) {
        this.cityService.deleteCityById(id);
    }

    /**
     * API for updating a city by its id
     * @param id            The id of the city to be updated.
     * @param name          The new name for the city (optional).
     * @param country       The new country for the city (optional).
     * @param state_region  The new state/region for the city (optional).
     * @param population    The new population for the city (optional).
     */
    @PutMapping(path = "{id}")
    public void updateCity(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String state_region,
            @RequestParam(required = false) Integer population
    ) {
        cityService.updateCity(id, name, country, state_region, population);
    }
}
