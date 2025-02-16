package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Doctor_Speciality;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Practice_Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeSpecialityRepository extends JpaRepository<Practice_Speciality, Long> {
    void deleteAllByPractice(Practice practice);
}
