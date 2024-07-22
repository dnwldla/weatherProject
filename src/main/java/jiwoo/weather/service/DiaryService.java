package jiwoo.weather.service;

import jiwoo.weather.domain.Diary;
import jiwoo.weather.repository.DiaryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Value("${openweathermap.key}")
    private String apiKey;



    public void createDiary(LocalDate date,String text) {
        diaryRepository.save(Diary.builder().
                            date(date)
                            .text(text)
                            .build());
    }

    //api 요청하기
    private String getWeatherString(){

        String apiUrl="http://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;
        System.out.println(apiUrl);
        return " ";
    }

    public String createDiary2(String text) {
        return text;
    }
}
