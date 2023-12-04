package com.gavrilo.cityweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CityWeatherApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
		assertNotNull(restTemplate);
		assertNotNull(objectMapper);
	}

	@Test
	void mainMethodStartsApplication() {
		CityWeatherApplication.main(new String[]{});
	}
}
