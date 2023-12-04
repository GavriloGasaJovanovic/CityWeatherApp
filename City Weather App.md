# City Weather App

## Description

This is a `JavaSpring` project using `PostgresSQL` for creating back-end application for:

* User registration to application
* User login to application
* All the CRUD operation for user to manipulate the data with condition that he access the API with his token

## Spring Boot initialization

* PROJECT: `Maven`
* LANGUAGE: `Java 17`
* SPRING BOOT VERSION: `3.2.0`
* DEPENDENCIES:
  * `Lombok`
  * `Spring Web`
  * `Spring Security`
  * `Spring Data JPA`
  * `PostgreSQL Driver`

## Postgres Setup

Make new database called `city-weather` in your Postgres database. To access the database. You need to change the `application.properties` where we setup how to access db. In this project, the properties are like this:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/city-weather
spring.datasource.username=postgres
spring.datasource.password=password 
```

## Coverage

The classes has been covered by `JUnit` test that are in `city-weather/src/test` module.

## Postman

In `city-weather/postman` module we can find the example of how to get City data using postman, as well as result of CityWeatherAPI collection test that covers one flow through all the functionalities of the application.