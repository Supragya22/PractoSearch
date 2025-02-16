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
import com.Practo_Search.PractoSearch.DTO.DoctorIdDTO;
import com.Practo_Search.PractoSearch.Repository.*;
import com.Practo_Search.PractoSearch.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private DoctorSpecialityRepository doctorSpecialityRepository;

    @Autowired
    private DoctorPracticeRepository doctorPracticeRepository;

    @Autowired
    private SearchIndexService searchIndexService; // Responsible for syncing Elasticsearch


    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    public DoctorDTO getDoctorById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        return doctor.map(d -> new DoctorDTO(
                d.getName(),
                d.getExperience(),
                d.getQualifications(),
                d.getDoctorSpecialities() != null
                        ? d.getDoctorSpecialities().stream()
                        .map(ds -> ds.getSpeciality().getName()) // Fetch Speciality names
                        .toList()
                        : Collections.emptyList(),
                d.getDoctorPractices() != null
                        ? d.getDoctorPractices().stream()
                        .map(dp -> dp.getPractice().getName()) // Fetch Practice names
                        .toList()
                        : Collections.emptyList()
        )).orElse(null);}


    public DoctorIdDTO saveDoctor(DoctorIdDTO doctorDTO) {
        // Convert DoctorIdDTO to Doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setQualifications(doctorDTO.getQualifications());

        // Save the Doctor entity to the database
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Handle relationships (Doctor-Speciality)
        if (doctorDTO.getSpecialityIds() != null) {
            for (Integer specialityId : doctorDTO.getSpecialityIds()) {
                Optional<Speciality> speciality = specialityRepository.findById(specialityId);
                speciality.ifPresent(s -> {
                    Doctor_Speciality doctorSpeciality = new Doctor_Speciality();
                    doctorSpeciality.setDoctor(savedDoctor);
                    doctorSpeciality.setSpeciality(s);
                    doctorSpecialityRepository.save(doctorSpeciality);  // Save Doctor-Speciality relationship
                });
            }
        }

        // Handle relationships (Doctor-Practice)
        if (doctorDTO.getPracticeIds() != null) {
            for (Integer practiceId : doctorDTO.getPracticeIds()) {
                Optional<Practice> practice = practiceRepository.findById(practiceId);
                practice.ifPresent(p -> {
                    Doctor_Practice doctorPractice = new Doctor_Practice();
                    doctorPractice.setDoctor(savedDoctor);
                    doctorPractice.setPractice(p);
                    doctorPracticeRepository.save(doctorPractice);  // Save Doctor-Practice relationship
                });
            }
        }

        // Sync the doctor with Elasticsearch (if necessary)
        searchIndexService.indexDoctor(savedDoctor); // Assuming this method indexes the saved doctor

        // Convert the saved doctor back to DoctorIdDTO and return it
        DoctorIdDTO savedDoctorDTO = new DoctorIdDTO(
                savedDoctor.getName(),
                savedDoctor.getExperience(),
                savedDoctor.getQualifications(),
                doctorDTO.getSpecialityIds(), // Speciality ids that were provided in input
                doctorDTO.getPracticeIds()   // Practice ids that were provided in input
        );

        return savedDoctorDTO;
    }



    // Update Doctor and sync changes to Elasticsearch
//    public DoctorDTO updateDoctor(int id, Doctor updatedDoctor) {
//        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
//        if (existingDoctorOpt.isPresent()) {
//            Doctor existingDoctor = existingDoctorOpt.get();
//            existingDoctor.setName(updatedDoctor.getName());
//            existingDoctor.setExperience(updatedDoctor.getExperience());
//            existingDoctor.setQualifications(updatedDoctor.getQualifications());
//            existingDoctor.setSpecialities(updatedDoctor.getSpecialities());
//            existingDoctor.setPractices(updatedDoctor.getPractices());
//
//            Doctor savedDoctor = doctorRepository.save(existingDoctor);
//            searchIndexService.indexDoctor(savedDoctor); // Update Elasticsearch
//            return new DoctorDTO(savedDoctor);
//        }
//        return null;
//    }

    @Transactional // Ensure all operations are done atomically
    public DoctorIdDTO updateDoctor(int id, DoctorIdDTO updatedDoctorDTO) {
        // Find the existing doctor
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (!optionalDoctor.isPresent()) {
            return null;  // Handle the case if the doctor is not found.
        }

        Doctor doctor = optionalDoctor.get();

        // Update the doctor details
        doctor.setName(updatedDoctorDTO.getName());
        doctor.setExperience(updatedDoctorDTO.getExperience());
        doctor.setQualifications(updatedDoctorDTO.getQualifications());

        // Save updated doctor to doctor table
        doctorRepository.save(doctor);

        // 2. Update doctor_specialities table (Handling specialties)
        updateDoctorSpecialities(doctor, updatedDoctorDTO.getSpecialityIds());

        // 3. Update doctor_practice table (Handling practices)
        updateDoctorPractices(doctor, updatedDoctorDTO.getPracticeIds());

        searchIndexService.indexDoctor(doctor);
        // Return updated DTO
        return new DoctorIdDTO(doctor);
    }

    private void updateDoctorSpecialities(Doctor doctor, List<Integer> specialityIds) {
        // First, delete existing doctor-speciality relationships
        doctorSpecialityRepository.deleteByDoctor(doctor);

        // Then, add the new specialities
        if (specialityIds != null && !specialityIds.isEmpty()) {
            specialityIds.forEach(specialityId -> {
                Optional<Speciality> speciality = specialityRepository.findById(specialityId);
                speciality.ifPresent(s -> {
                    Doctor_Speciality doctorSpeciality = new Doctor_Speciality();
                    doctorSpeciality.setDoctor(doctor);
                    doctorSpeciality.setSpeciality(s);
                    doctorSpecialityRepository.save(doctorSpeciality);  // Save new speciality for the doctor
                });
            });
        }
    }

    private void updateDoctorPractices(Doctor doctor, List<Integer> practiceIds) {
        // First, delete existing doctor-practice relationships
        doctorPracticeRepository.deleteByDoctor(doctor);

        // Then, add the new practices
        if (practiceIds != null && !practiceIds.isEmpty()) {
            practiceIds.forEach(practiceId -> {
                Optional<Practice> practice = practiceRepository.findById(practiceId);
                practice.ifPresent(p -> {
                    Doctor_Practice doctorPractice = new Doctor_Practice();
                    doctorPractice.setDoctor(doctor);
                    doctorPractice.setPractice(p);
                    doctorPracticeRepository.save(doctorPractice);  // Save new practice for the doctor
                });
            });
        }
    }

    // Delete doctor from MySQL and Elasticsearch
//    public void deleteDoctor(int id) {
//        doctorRepository.deleteById(id);
//        searchIndexService.deleteDoctorFromIndex(id); // Remove from Elasticsearch
//    }
}
