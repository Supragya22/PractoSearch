package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    @Query("SELECT s FROM Speciality s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Speciality> searchBySpecialityName(String keyword);
}
