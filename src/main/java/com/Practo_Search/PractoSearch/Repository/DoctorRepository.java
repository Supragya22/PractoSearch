package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
//
//    @Query("SELECT d FROM Doctor d JOIN d.specialities s " +
//            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Doctor> searchByDoctorNameOrSpeciality(String keyword);
//
//}

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT DISTINCT d FROM Doctor d " +
            "LEFT JOIN Doctor_Speciality ds ON d.id = ds.doctor.id " +
            "LEFT JOIN Speciality s ON ds.speciality.id = s.id " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Doctor> searchByDoctorNameOrSpeciality(String keyword);
}
