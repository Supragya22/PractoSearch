package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {

//    @Query("SELECT p FROM Practice p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.state) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Practice> searchByPracticeNameOrCityOrState(String keyword);

    @Query("SELECT p FROM Practice p JOIN p.specialities s " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.state) LIKE LOWER(CONCAT('%', :keyword, '%'))"+
            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Practice> searchByPracticeNameOrCityOrStateOrSpeciality(String keyword);

}


