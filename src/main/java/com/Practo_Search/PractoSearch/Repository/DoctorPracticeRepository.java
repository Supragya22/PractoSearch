package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Doctor_Practice;
import com.Practo_Search.PractoSearch.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorPracticeRepository extends JpaRepository<Doctor_Practice, Long> {
    // Delete all doctor-practice relationships for a specific doctor
    void deleteByDoctor(Doctor doctor);
    void deleteAllByPractice(Practice practice);
}
