package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorDTO getDoctorById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.map(d -> new DoctorDTO(
                d.getName(),
                d.getExperience(),
                d.getQualifications(),
                d.getSpecialities() != null ? d.getSpecialities().stream().map(Speciality::getName).toList() : Collections.emptyList(),
                d.getPractices() != null ? d.getPractices().stream().map(Practice::getName).toList() : Collections.emptyList()
        )).orElse(null);
    }

}
