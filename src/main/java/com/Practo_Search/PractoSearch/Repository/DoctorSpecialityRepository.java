package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Doctor_Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSpecialityRepository extends JpaRepository<Doctor_Speciality, Long> {
    // Delete all doctor-speciality relationships for a specific doctor
    void deleteByDoctor(Doctor doctor);
}

