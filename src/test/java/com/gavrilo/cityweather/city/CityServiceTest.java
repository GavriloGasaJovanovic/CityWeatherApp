package com.gavrilo.cityweather.city;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testGetCityByName_Success() throws JsonProcessingException {
        String cityName = "Nis";
        City city = new City(1, cityName, "Serbia", "Nis", 300000);
        when(cityRepository.findCityByName(cityName)).thenReturn(Optional.of(city));

        String mockApiResponse = "{\"current\": {\"temp_c\": 20.0}}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockApiResponse);

        JsonNode root = mock(JsonNode.class);
        JsonNode current = mock(JsonNode.class);

        when(objectMapper.readTree(mockApiResponse)).thenReturn(root);
        when(root.path("current")).thenReturn(current);
        when(current.path("temp_c")).thenReturn(current);
        when(current.asDouble()).thenReturn(20.0);

        assertDoesNotThrow(() -> {
            CityWeatherDTO cityWeatherDTO = cityService.getCityByName(cityName);
            assertNotNull(cityWeatherDTO);
            assertEquals(cityName, cityWeatherDTO.getName());
            assertEquals(20, cityWeatherDTO.getTemp_c());
        });

        verify(restTemplate).getForObject(contains(cityName), eq(String.class));
    }

    @Test
    public void testGetCityByName_Success_catchError() throws JsonProcessingException {
        String cityName = "Nis";
        City city = new City(1, cityName, "Serbia", "Nis", 300000);
        when(cityRepository.findCityByName(cityName)).thenReturn(Optional.of(city));

        String mockApiResponse = "{\"current\": {\"temp_c\": 20.0}}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockApiResponse);

        when(objectMapper.readTree(mockApiResponse)).thenThrow(JsonProcessingException.class);

        assertDoesNotThrow(() -> {
            CityWeatherDTO cityWeatherDTO = cityService.getCityByName(cityName);
            assertNotNull(cityWeatherDTO);
            assertEquals(cityName, cityWeatherDTO.getName());
            assertEquals(0, cityWeatherDTO.getTemp_c());
        });

        verify(restTemplate).getForObject(contains(cityName), eq(String.class));
    }

    @Test
    public void testGetCityByName_CityNotFound() {
        String cityName = "NonExistentCity";
        when(cityRepository.findCityByName(cityName)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.getCityByName(cityName);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("City not found with name: " + cityName, exception.getReason());
    }

    @Test
    public void testAddNewCity_Success() {
        City city = new City(1, "Nis", "Serbia", "Nis", 300000);
        when(cityRepository.findCityById(city.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            cityService.addNewCity(city);
        });
    }

    @Test
    public void testAddNewCity_CityAlreadyExists() {
        City city = new City(1, "Nis", "Serbia", "Nis", 300000);
        when(cityRepository.findCityById(city.getId())).thenReturn(Optional.of(city));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.addNewCity(city);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("City with that id or name already exists!", exception.getReason());
    }

    @Test
    public void testDeleteCityById_Success() {
        int cityId = 1;
        when(cityRepository.existsById(cityId)).thenReturn(true);

        assertDoesNotThrow(() -> {
            cityService.deleteCityById(cityId);
        });
    }

    @Test
    public void testDeleteCityById_CityNotFound() {
        int cityId = 1;
        when(cityRepository.existsById(cityId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.deleteCityById(cityId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("City with id= " + cityId + " doesn't exists.", exception.getReason());
    }

    @Test
    public void testUpdateCity_Success() {
        int cityId = 1;
        City city = new City(cityId, "Nis", "Serbia", "Nis", 300000);
        when(cityRepository.findCityById(cityId)).thenReturn(Optional.of(city));

        assertDoesNotThrow(() -> {
            cityService.updateCity(cityId, "NewName", "NewCountry", "NewState", 500000);
        });
    }

    @Test
    public void testUpdateCity_CityNotFound() {
        int cityId = 1;
        when(cityRepository.findCityById(cityId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(cityId, "NewName", "NewCountry", "NewState", 500000);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("City with id= " + cityId + " doesn't exists.", exception.getReason());
    }

    @Test
    public void testUpdateCity_NameTaken() {
        int cityId = 1;
        City city = new City(cityId, "Nis", "Serbia", "Nis", 300000);
        when(cityRepository.findCityById(cityId)).thenReturn(Optional.of(city));
        when(cityRepository.findCityByName("NewName")).thenReturn(Optional.of(new City()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(cityId, "NewName", "NewCountry", "NewState", 500000);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("name taken", exception.getReason());
    }
}