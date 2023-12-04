package com.gavrilo.cityweather.city;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

/**
 * CityService class for handling city-related operations.
 */
@Service
public class CityService {


    private final CityRepository cityRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * CityService constructor
     * @param cityRepository The repository for City entities.
     * @param restTemplate   RestTemplate for making HTTP requests.
     * @param objectMapper   ObjectMapper for JSON processing.
     */
    public CityService(CityRepository cityRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.cityRepository = cityRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Method for getting city data by its name
     * @param name The name of the city.
     * @return CityWeatherDTO containing information about the city.
     */
    public CityWeatherDTO getCityByName(String name) {
        Optional<City> cityOptional = cityRepository.findCityByName(name);
        if(cityOptional.isPresent()) {
            int tmp = getCurrentTemperature(name);
            City city = cityOptional.get();
            return new CityWeatherDTO(city, tmp);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"City not found with name: " + name);
        }
    }

    /**
     * Method that adds a new city to the database
     * @param city The City object to be added.
     */
    public void addNewCity(City city) {
        Optional<City> cityOptional = cityRepository.findCityById(city.getId());
        Optional<City> cityOptional2 = cityRepository.findCityByName(city.getName());
        if(cityOptional.isPresent() || cityOptional2.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"City with that id or name already exists!");
        } else {
            cityRepository.save(city);
        }
    }

    /**
     * Method for deleting a city by its id
     * @param id The id of the city to be deleted.
     */
    public void deleteCityById(Integer id) {
        boolean exists = cityRepository.existsById(id);
        if(!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"City with id= " + id + " doesn't exists.");
        } else {
            cityRepository.deleteById(id);
        }
    }

    /**
     * Method for updating a city with the given id
     * @param id           The id of the city to be updated.
     * @param name         The new name for the city (optional).
     * @param country      The new country for the city (optional).
     * @param stateRegion  The new state/region for the city (optional).
     * @param population   The new population for the city (optional).
     */
    @Transactional
    public void updateCity(Integer id, String name, String country, String stateRegion, Integer population) {
        City city = cityRepository.findCityById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"City with id= " + id + " doesn't exists."
                ));

        if(name != null && name.length() > 0 && !Objects.equals(city.getName(),name)) {
            Optional<City> cityOptional = cityRepository
                    .findCityByName(name);
            if(cityOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "name taken");
            } else {
                city.setName(name);
            }
        }

        if(country != null && country.length() > 0 && !Objects.equals(city.getCountry(), country)) {
            city.setCountry(country);
        }

        if(stateRegion != null && stateRegion.length() > 0 && !Objects.equals(city.getStateRegion(), stateRegion)) {
            city.setStateRegion(stateRegion);
        }

        if(population != null && population > 0 && !Objects.equals(city.getPopulation(), population)) {
            city.setPopulation(population);
        }
    }

    /**
     * Method that fetches data from weatherapi for a given city
     * @param cityName The name of the city.
     * @return The temperature in the given city.
     */
    private int getCurrentTemperature(String cityName) {
        String apiKey = "5f6f8f922eeb4103b9e163711230212";
        String apiUrl = "http://api.weatherapi.com/v1/current.json";

        String url = apiUrl + "?key=" + apiKey + "&q=" + cityName;
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode current = root.path("current");
            double tempCelsius = current.path("temp_c").asDouble();
            return (int) Math.round(tempCelsius);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
