//package com.Practo_Search.PractoSearch.Service;
//
//import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
//import com.Practo_Search.PractoSearch.model.Doctor;
//import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Data
//public class DoctorService {
//
//    @Autowired
//    private final DoctorRepository doctorRepository;
//
//    public List<DoctorDTO> getDoctorsBySpeciality(String specialityName) {
//        List<Doctor> doctors = doctorRepository.findBySpecialityName(specialityName);
//        return doctors.stream()
//                      .map(doctor -> new DoctorDTO(
//                              doctor.getId(),
//                              doctor.getName(),
//                              doctor.getExperience(),
//                              doctor.getQualifications()))
//                      .collect(Collectors.toList());
//    }
//}
