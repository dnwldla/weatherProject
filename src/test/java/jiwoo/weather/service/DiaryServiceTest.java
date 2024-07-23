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



}