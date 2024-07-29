package jiwoo.weather.repository;


import jiwoo.weather.domain.DateWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DateWeatherRepository extends JpaRepository<DateWeather, LocalDate> {

}
