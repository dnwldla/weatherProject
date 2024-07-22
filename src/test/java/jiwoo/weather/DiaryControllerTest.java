package jiwoo.weather;

import jiwoo.weather.controller.DiaryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DiaryControllerTest {

    @Autowired
    private DiaryController diaryController;

    @Test
    public void test2(){
        assertThat(diaryController).isNotNull();
    }

}