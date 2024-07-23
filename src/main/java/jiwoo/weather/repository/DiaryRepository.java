package jiwoo.weather.repository;

import jiwoo.weather.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Integer> {
    List<Diary> findByDate(LocalDate date);
    List<Diary> findAllByDateBetween(LocalDate start, LocalDate end);

    //특정 날짜에서 가장 첫번째 일기 가져오기
    Diary getFirstByDate(LocalDate date);

    void deleteAllByDate(LocalDate date);
}
