package jiwoo.weather.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity(name="diary")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   private String weather;
   private String icon;
   private double temperature;
   @NotNull
   private String text;
   private LocalDate date;


}
