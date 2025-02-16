package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
//public interface PracticeRepository extends JpaRepository<Practice, Integer> {
//
//    @Query("SELECT p FROM Practice p JOIN p.specialities s " +
//            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.state) LIKE LOWER(CONCAT('%', :keyword, '%'))"+
//            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Practice> searchByPracticeNameOrCityOrStateOrSpeciality(String keyword);
//
//}

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {

    @Query("SELECT DISTINCT p FROM Practice p " +
            "LEFT JOIN Practice_Speciality ps ON p.id = ps.practice.id " +
            "LEFT JOIN Speciality s ON ps.speciality.id = s.id " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.state) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Practice> searchByPracticeNameOrCityOrStateOrSpeciality(String keyword);
    Optional<Practice> findById(Integer id);
}


