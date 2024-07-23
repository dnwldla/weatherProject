package jiwoo.weather.service;
import jiwoo.weather.domain.Diary;
import jiwoo.weather.repository.DiaryRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Value("${openweathermap.key}")
    private String apiKey;



    public void createDiary(LocalDate date,String text) {
        //날씨 데이터 가져오기
        String weatherData=getWeatherString();

        //받아온 날씨 json 파싱하기
        Map<String,Object> resultMap=parseWeather(weatherData);

        //파싱된 데이터 db에 넣기
        Diary diary=Diary.builder()
                        .weather(resultMap.get("main").toString())
                        .icon(resultMap.get("icon").toString())
                        .temperature((Double) resultMap.get("temp"))
                        .text(text)
                        .date(date)
                        .build();


        diaryRepository.save(diary);
    }

    //api 요청하기
    private String getWeatherString(){

        String apiUrl="http://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;

        //json 파싱
        try{
            URL url=new URL(apiUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode=connection.getResponseCode();
            BufferedReader br;

            if (responseCode==200){
                br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else{
                br=new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response=new StringBuilder();

            while ((inputLine=br.readLine())!=null){
                response.append(inputLine);
            }
            br.close();


            return response.toString();
        }catch(Exception e){
            return "failed to response";
        }



    }

    //날씨데이터 json으로 파싱하기
    private Map<String,Object> parseWeather(String jsonString){
        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject=(JSONObject)jsonParser.parse(jsonString);
        }catch(ParseException e){

            throw new RuntimeException(e);
        }
        Map<String,Object> resultMap=new HashMap<>();

        JSONObject mainData=(JSONObject)jsonObject.get("main");
        resultMap.put("temp",mainData.get("temp"));

        JSONArray weatherArray=(JSONArray) jsonObject.get("weather");
        JSONObject weatherData=(JSONObject) weatherArray.get(0);

        resultMap.put("main",weatherData.get("main"));
        resultMap.put("icon",weatherData.get("icon"));

        return resultMap;




    }


    //날짜로 다이어리 조회하기
    public List<Diary> readDiary(LocalDate date) {

        return diaryRepository.findByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate,LocalDate endDate){
        return diaryRepository.findAllByDateBetween(startDate,endDate);
    }

    public Diary updateDiary(LocalDate date, String text) {
        Diary diary=diaryRepository.getFirstByDate(date);

        //수정하기
        diary.setText(text);
        //id값이 있는 상태에서 save하면 row 추가 안됨
        diaryRepository.save(diary);

        return diary;
    }

    @Transactional
    //삭제하기
    public String deleteDiary(LocalDate date) {
       diaryRepository.deleteAllByDate(date);



        return "삭제 완료";

    }
}
