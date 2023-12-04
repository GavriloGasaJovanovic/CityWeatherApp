package com.gavrilo.cityweather.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and manipulating City entities in the database.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * Query for finding a city by its name.
     * @param name The name of the city.
     * @return An Optional containing the found City or empty if not found.
     */
    @Query("SELECT c FROM City c WHERE c.name = ?1")
    Optional<City> findCityByName(String name);

    /**
     * Query for finding a city by its id.
     * @param id The id of the city.
     * @return An Optional containing the found City or empty if not found.
     */
    @Query("SELECT c FROM City c WHERE c.id = ?1")
    Optional<City> findCityById(Integer id);
}
