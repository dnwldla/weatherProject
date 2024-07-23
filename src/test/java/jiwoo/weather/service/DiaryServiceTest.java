package jiwoo.weather.service;

import jiwoo.weather.domain.Diary;
import jiwoo.weather.repository.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

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


    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private DiaryService diaryService;

    @Test
    void readDiariesSuccess() {

        //given
        given(diaryRepository.save(diaries.get(0))).willReturn(diaries.get(0));
        //given
        given(diaryRepository.findAll()).willReturn(diaries);

        //when
        List<Diary> diaryTest=diaryService.readDiaries(LocalDate.parse("2022-07-22"),LocalDate.parse("2022-07-23"));

        assertEquals(diaries,diaryTest);

    }

}