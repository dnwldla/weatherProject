package jiwoo.weather.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity(name="dateweather")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateWeather {
    @Id
    private LocalDate id;
    private String weather;
    private String icon;
    private double temperature;




}
