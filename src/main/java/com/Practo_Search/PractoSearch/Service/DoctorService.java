//package com.Practo_Search.PractoSearch.Service;
//
//import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
//import com.Practo_Search.PractoSearch.model.Doctor;
//import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
//import com.Practo_Search.PractoSearch.model.Practice;
//import com.Practo_Search.PractoSearch.model.Speciality;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Service
//public class DoctorService {
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    public DoctorDTO getDoctorById(int id) {
//        Optional<Doctor> doctor = doctorRepository.findById(id);
//        return doctor.map(d -> new DoctorDTO(
//                d.getName(),
//                d.getExperience(),
//                d.getQualifications(),
//                d.getSpecialities() != null ? d.getSpecialities().stream().map(Speciality::getName).toList() : Collections.emptyList(),
//                d.getPractices() != null ? d.getPractices().stream().map(Practice::getName).toList() : Collections.emptyList()
//        )).orElse(null);
//    }
//
//}
package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SearchIndexService searchIndexService; // Responsible for syncing Elasticsearch

    // Fetch Doctor directly from MySQL
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

    // Save Doctor in MySQL and index in Elasticsearch
    public Doctor saveDoctor(Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        searchIndexService.indexDoctor(savedDoctor); // Sync with Elasticsearch
        return savedDoctor;
    }

    // Update Doctor and sync changes to Elasticsearch
    public Doctor updateDoctor(int id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setName(updatedDoctor.getName());
            existingDoctor.setExperience(updatedDoctor.getExperience());
            existingDoctor.setQualifications(updatedDoctor.getQualifications());
            existingDoctor.setSpecialities(updatedDoctor.getSpecialities());
            existingDoctor.setPractices(updatedDoctor.getPractices());

            Doctor savedDoctor = doctorRepository.save(existingDoctor);
            searchIndexService.indexDoctor(savedDoctor); // Update Elasticsearch
            return savedDoctor;
        }
        return null;
    }

    // Delete doctor from MySQL and Elasticsearch
//    public void deleteDoctor(int id) {
//        doctorRepository.deleteById(id);
//        searchIndexService.deleteDoctorFromIndex(id); // Remove from Elasticsearch
//    }
}
