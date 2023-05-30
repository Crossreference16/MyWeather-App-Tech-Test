package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;
  @Autowired
//  private VisualcrossingRepository visualcrossingRepository;
  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  // TODO: given two city names, compare the length of the daylight hours and return the city with the longest day
  @GetMapping("/compare/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    CityInfo city1Info = visualcrossingRepository.getByCity(city1);
    CityInfo city2Info = visualcrossingRepository.getByCity(city2);

    String city1Sunrise = city1Info.currentConditions().sunrise;
    String city1Sunset = city1Info.currentConditions().sunset;
    int city1DaylightHours = calculateDaylightHours(city1Sunrise, city1Sunset);

    String city2Sunrise = city2Info.currentConditions().sunrise;
    String city2Sunset = city2Info.currentConditions().sunset;
    int city2DaylightHours = calculateDaylightHours(city2Sunrise, city2Sunset);

    if (city1DaylightHours > city2DaylightHours) {
      return ResponseEntity.ok(city1 + " has the longest day.");
    } else if (city2DaylightHours > city1DaylightHours) {
      return ResponseEntity.ok(city2 + " has the longest day.");
    } else {
      return ResponseEntity.ok("Both cities have the same daylight hours.");
    }
  }

  private int calculateDaylightHours(String sunrise, String sunset) {
    LocalDateTime sunriseTime = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    LocalDateTime sunsetTime = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    Duration daylightDuration = Duration.between(sunriseTime, sunsetTime);
    long daylightMinutes = daylightDuration.toMinutes();
    return (int) daylightMinutes;
  }

  // TODO: given two city names, check which city its currently raining in

  @GetMapping("/raincheck/{city1}/{city2}")
  public ResponseEntity<String> rainCheck(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    CityInfo city1Info = VisualcrossingRepository.getByCity(city1);
    CityInfo city2Info = VisualcrossingRepository.getByCity(city2);

    boolean city1Raining = isRaining(city1Info);
    boolean city2Raining = isRaining(city2Info);

    if (city1Raining && city2Raining) {
      return ResponseEntity.ok("It is raining in both " + city1 + " and " + city2 + ".");
    } else if (city1Raining) {
      return ResponseEntity.ok("It is raining in " + city1 + ".");
    } else if (city2Raining) {
      return ResponseEntity.ok("It is raining in " + city2 + ".");
    } else {
      return ResponseEntity.ok("It is not currently raining in either city.");
    }
  }

  private boolean isRaining(CityInfo cityInfo) {
    //I Tried to implement the followeing steps to make this work:
    //Implement the logic to check if it is currently raining in the city
    // You can use the weather conditions or precipitation data from the CityInfo object
    // Return true if it is raining, false otherwise


  }
}


