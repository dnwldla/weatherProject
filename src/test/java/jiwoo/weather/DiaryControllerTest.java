package jiwoo.weather;

import jiwoo.weather.controller.DiaryController;
import jiwoo.weather.domain.Diary;
import jiwoo.weather.service.DiaryService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiaryController.class)
public class DiaryControllerTest {


    @MockBean
    private DiaryService diaryService;

    @Autowired
    private MockMvc mockMvc;

    //기간으로 다이어리 조회
    @Test
    void successReadDiaries() throws Exception{
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
                        .date(LocalDate.parse("2022-07-22")).

                        build()
        );

        //given
        given(diaryService.readDiaries(LocalDate.parse("2022-07-22"),
                LocalDate.parse("2022-07-23")))
                .willReturn(diaries);


        //then
        mockMvc.perform(get("/read/diaries?startDate=2022-07-22&endDate=2022-07-23"))
                .andExpect(jsonPath("$[0].weather").value("humid"))
                .andExpect(jsonPath("$[1].weather").value("sunny"))
                .andExpect(status().isOk());


    }

    //일기수정하기
    @Test
    void successUpdateDiary() throws Exception{
        //given
        given(diaryService.updateDiary(LocalDate.parse("2022-07-22"),"수정했는디?"))
                .willReturn(Diary.builder().
                        weather("humid").
                        icon("icon1")
                        .temperature(10d)
                        .text("수정했는디?")
                        .date(LocalDate.parse("2022-07-22"))
                        .build());

        mockMvc.perform(put("/update/diary?date=2022-07-22")
                        .content("수정했는디?")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.text").value("수정했는디?"))
                .andExpect(status().isOk());

    }

    @Test
    void successDeleteDiary() throws Exception{
        given(diaryService.deleteDiary(LocalDate.parse("2022-07-22")))
                .willReturn("삭제 완료");

        mockMvc.perform(delete("/delete/diary")
                        .param("date","2022-07-22"))
                .andExpect(content().string("삭제 완료"))
                .andExpect(status().isOk());
    }

}