package jiwoo.weather.controller;


import jiwoo.weather.domain.Diary;
import jiwoo.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DiaryController {


    @Autowired
    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    void createDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestBody String text){
            diaryService.createDiary(date,text);
    }

    //특정 날짜의 다이어리 조회하기
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

    return diaryService.readDiary(date);
    }

    //기간으로 다이어리 조회하기
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return diaryService.readDiaries(startDate,endDate);
    }

    //일기 수정하기
    @PutMapping("/update/diary")
    Diary updateDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestBody String text){
        return diaryService.updateDiary(date,text);
    }

    //일기 삭제하기
    @DeleteMapping("/delete/diary")
    String deleteDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return diaryService.deleteDiary(date);
    }







}
