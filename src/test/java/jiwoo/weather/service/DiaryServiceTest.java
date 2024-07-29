package jiwoo.weather.service;

import jiwoo.weather.domain.DateWeather;
import jiwoo.weather.domain.Diary;
import jiwoo.weather.repository.DiaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    List<Diary> diaries=List.of(
            Diary.builder().
                    weather("humid").
                    icon("icon1")
                    .temperature(10d)
                    .text("hello")
                    .date(LocalDate.parse("2022-07-22")).
                    build(),

            Diary.builder().
                    weather("sunny").
                    icon("icon2")
                    .temperature(15d)
                    .text("hello2")
                    .date(LocalDate.parse("2022-07-23")).
                    build()
    );

    Diary diary=Diary.builder()
            .weather("sunny")
            .icon("icontest")
            .temperature(10d)
            .text("testText")
            .date(LocalDate.parse("2022-08-09"))
            .build();


    @Mock
    private DiaryRepository diaryRepository;

    @Mock
    private DiaryService testDiaryService;

    @Mock
    private HttpURLConnection connection;

    @InjectMocks
    private DiaryService diaryService;



    @Test
    void readDiariesSuccess() {

        given(diaryRepository.findAllByDateBetween(LocalDate.parse("2022-07-22"),LocalDate.parse("2022-07-23")))
                .willReturn(diaries);

        List<Diary> diaryDto=diaryService.readDiaries(LocalDate.parse("2022-07-22"),LocalDate.parse("2022-07-23"));

        //then
        assertEquals(diaryDto.get(0).getIcon(),"icon1");

    }

    @Test
    void readDiarySuccess(){

        List<Diary> listofDiary=List.of(
                Diary.builder().
                        weather("humid").
                        icon("icon1")
                        .temperature(10d)
                        .text("hello")
                        .date(LocalDate.parse("2022-07-22")).
                        build()

        );

        given(diaryRepository.findByDate(LocalDate.parse("2022-07-22")))
                .willReturn(listofDiary);

        List<Diary> diaryList=diaryService.readDiary(LocalDate.parse("2022-07-22"));

        assertEquals(diaryList.size(),1);

    }

    @Test
    void updateDiarySuccess(){
        given(diaryRepository.getFirstByDate(LocalDate.parse("2022-07-22")))
                .willReturn( Diary.builder().
                        weather("humid").
                        icon("icon1")
                        .temperature(10d)
                        .text("hello")
                        .date(LocalDate.parse("2022-07-22")).
                        build());

        //when
        Diary diary=diaryService.updateDiary(LocalDate.parse("2022-07-22"),"수정댐!");

        assertEquals(diary.getText(),"수정댐!");
    }



    @Test
    void getWeatherStringSuccess() throws IOException {
        String mockWeatherString = "{ \"main\": { \"temp\": 25.0 }, \"weather\":" +
                " [ { \"main\": \"Clear\", \"icon\": \"01d\" } ] }";


    }

    @Test //스케줄링 시 데이터 요청 테스트코드
    void getWeatherFromApiSuccess(){
        //given
        String mockWeatherString = "{ \"main\": { \"temp\": 25.0 }, \"weather\":" +
                                   " [ { \"main\": \"Clear\", \"icon\": \"01d\" } ] }";

        //given
        Map<String,Object> parseWeather=new HashMap<String, Object>() {{
            put("main", "humid");
            put("icon", "testicon");
            put("temp", 20d);
        }};

        given(testDiaryService.getWeatherString())
                .willReturn(mockWeatherString);
        given(testDiaryService.parseWeather(mockWeatherString))
                .willReturn(parseWeather);

        //when
//        DateWeath
//        er dateWeather=diaryService.getWeatherFromApi();
//
//        //then
//        assertEquals(dateWeather.getWeather(),"humid");


    }




}