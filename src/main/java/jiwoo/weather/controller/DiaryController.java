package jiwoo.weather.controller;


import jiwoo.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {


    @Autowired
    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    void createDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestBody String text){
            diaryService.createDiary(date,text);
    }

//    @PostMapping("/create/diary")
//    void createDiary(@RequestBody String text){
//        diaryService.createDiary2(text);
//    }




}
