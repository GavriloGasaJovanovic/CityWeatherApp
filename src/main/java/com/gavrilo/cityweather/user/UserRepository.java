package com.gavrilo.cityweather.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves a user by their email.
     * @param email The email of the user to retrieve.
     * @return An Optional containing the user if found, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}
