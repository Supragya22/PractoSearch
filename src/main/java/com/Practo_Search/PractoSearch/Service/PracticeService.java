//package com.Practo_Search.PractoSearch.Service;
//
//import com.Practo_Search.PractoSearch.DTO.PracticeDTO;
//import com.Practo_Search.PractoSearch.Repository.SearchIndexRepository;
//import com.Practo_Search.PractoSearch.model.Practice;
//import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
//import com.Practo_Search.PractoSearch.model.SearchIndex;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class PracticeService {
//
//    @Autowired
//    private PracticeRepository practiceRepository;
//
//    @Autowired
//    private SearchIndexRepository searchIndexRepository;
//
//    public PracticeDTO getPracticeById(int id) {
//        Optional<Practice> practice = practiceRepository.findById(id);
//        return practice.map(p -> new PracticeDTO(p.getName(), p.getAddress(), p.getState(), p.getCity(), p.getWebsite())).orElse(null);
//    }
//
//    public void indexPractice(Practice practice) {
//        SearchIndex searchIndex = new SearchIndex();
//        searchIndex.setId("P-" + practice.getId()); // Prefix ID
//        searchIndex.setType("Practice");
//        searchIndex.setName(practice.getName());
//        searchIndex.setAdditionalInfo(practice.getCity() + ", " + practice.getState());
//
//        searchIndexRepository.save(searchIndex);
//    }
//
//}


package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.PracticeDTO;
import com.Practo_Search.PractoSearch.DTO.PracticeIdDTO;
import com.Practo_Search.PractoSearch.Repository.*;
import com.Practo_Search.PractoSearch.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private PracticeSpecialityRepository practiceSpecialityRepository;

    @Autowired
    private DoctorPracticeRepository doctorPracticeRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SearchIndexService searchIndexService; // Manages Elasticsearch syncing

    // Fetch practice details from MySQL
//    public PracticeDTO getPracticeById(int id) {
//        Optional<Practice> practice = practiceRepository.findById(id);
//        return practice.map(p -> new PracticeDTO(
//                p.getName(),
//                p.getAddress(),
//                p.getState(),
//                p.getCity(),
//                p.getWebsite(),
//                p.getDoctors()!= null ? p.getDoctors().stream().map(Doctor::getName).toList() : Collections.emptyList(),
//                p.getSpecialities()!= null ? p.getSpecialities().stream().map(Speciality::getName).toList() : Collections.emptyList()
//        )).orElse(null);
//    }

    public PracticeDTO getPracticeById(int id) {
        Optional<Practice> practice = practiceRepository.findById(id);

        return practice.map(p -> new PracticeDTO(
                p.getName(),
                p.getAddress(),
                p.getState(),
                p.getCity(),
                p.getWebsite(),
                p.getDoctorPractices() != null
                        ? p.getDoctorPractices().stream()
                        .map(dp -> dp.getDoctor().getName()) // Fetch doctor names from Doctor_Practice
                        .toList()
                        : Collections.emptyList(),
                p.getPracticeSpecialities() != null
                        ? p.getPracticeSpecialities().stream()
                        .map(ps -> ps.getSpeciality().getName()) // Fetch specialities from Practice_Speciality
                        .toList()
                        : Collections.emptyList()
        )).orElseThrow(() -> new RuntimeException("Practice with ID " + id + " not found"));
    }


    // Save Practice in MySQL & sync to Elasticsearch
    public PracticeIdDTO savePractice(PracticeIdDTO practiceDTO) {
        // Convert PracticeIdDTO to Practice entity
        Practice practice = new Practice();
        practice.setName(practiceDTO.getName());
        practice.setAddress(practiceDTO.getAddress());
        practice.setState(practiceDTO.getState());
        practice.setCity(practiceDTO.getCity());
        practice.setWebsite(practiceDTO.getWebsite());

        // Save the Practice entity to the database
        Practice savedPractice = practiceRepository.save(practice);

        // Optionally, you may want to handle relationships (e.g., Practice-Speciality and Doctor-Practice)
        if (practiceDTO.getSpecialityIds() != null) {
            for (Integer specialityId : practiceDTO.getSpecialityIds()) {
                Optional<Speciality> speciality = specialityRepository.findById(specialityId);
                speciality.ifPresent(s -> {
                    Practice_Speciality practiceSpeciality = new Practice_Speciality();
                    practiceSpeciality.setPractice(savedPractice);
                    practiceSpeciality.setSpeciality(s);
                    practiceSpecialityRepository.save(practiceSpeciality);  // Save Practice-Speciality relationship
                });
            }
        }

        if (practiceDTO.getDoctorIds() != null) {
            for (Integer doctorId : practiceDTO.getDoctorIds()) {
                Optional<Doctor> doctor = doctorRepository.findById(doctorId);
                doctor.ifPresent(d -> {
                    Doctor_Practice doctorPractice = new Doctor_Practice();
                    doctorPractice.setPractice(savedPractice);
                    doctorPractice.setDoctor(d);
                    doctorPracticeRepository.save(doctorPractice);  // Save Doctor-Practice relationship
                });
            }
        }

        // Sync the practice with Elasticsearch
        searchIndexService.indexPractice(savedPractice); // Assuming this method indexes the saved practice

        // Convert the saved practice back to PracticeIdDTO and return it
        PracticeIdDTO savedPracticeDTO = new PracticeIdDTO(
                savedPractice.getName(),
                savedPractice.getAddress(),
                savedPractice.getState(),
                savedPractice.getCity(),
                savedPractice.getWebsite(),
                practiceDTO.getDoctorIds(), // You might need to fetch the doctors again if necessary
                practiceDTO.getSpecialityIds() // Similarly for specialties
        );

        return savedPracticeDTO;
    }


    // Update Practice & sync changes to Elasticsearch
//    public Practice updatePractice(int id, Practice updatedPractice) {
//        Optional<Practice> existingPracticeOpt = practiceRepository.findById(id);
//        if (existingPracticeOpt.isPresent()) {
//            Practice existingPractice = existingPracticeOpt.get();
//            existingPractice.setName(updatedPractice.getName());
//            existingPractice.setAddress(updatedPractice.getAddress());
//            existingPractice.setState(updatedPractice.getState());
//            existingPractice.setCity(updatedPractice.getCity());
//            existingPractice.setWebsite(updatedPractice.getWebsite());
//            existingPractice.setDoctors(updatedPractice.getDoctors());
//            existingPractice.setSpecialities(updatedPractice.getSpecialities());
//
//            Practice savedPractice = practiceRepository.save(existingPractice);
//            searchIndexService.indexPractice(savedPractice); // Update Elasticsearch
//            return savedPractice;
//        }
//        return null;
//    }

    public PracticeIdDTO updatePractice(int id, PracticeIdDTO updatedPracticeDTO) {
        Optional<Practice> optionalPractice = practiceRepository.findById(id);
        if (optionalPractice.isEmpty()) {
            return null; // Practice not found
        }

        Practice practice = optionalPractice.get();

        // Update practice details
        practice.setName(updatedPracticeDTO.getName());
        practice.setAddress(updatedPracticeDTO.getAddress());
        practice.setCity(updatedPracticeDTO.getCity());
        practice.setState(updatedPracticeDTO.getState());
        practice.setWebsite(updatedPracticeDTO.getWebsite());

        // Save updated practice
        practiceRepository.save(practice);

        // Update Practice-Speciality associations
        updatePracticeSpecialities(practice, updatedPracticeDTO.getSpecialityIds());

        // Update Doctor-Practice associations
        updateDoctorPractices(practice, updatedPracticeDTO.getDoctorIds());

        // Return the updated PracticeIdDTO
        return new PracticeIdDTO(practice.getName(), practice.getAddress(), practice.getState(), practice.getCity(), practice.getWebsite(), updatedPracticeDTO.getDoctorIds(), updatedPracticeDTO.getSpecialityIds());
    }

    private void updatePracticeSpecialities(Practice practice, List<Integer> specialityIds) {
        // Remove old associations
        practiceSpecialityRepository.deleteAllByPractice(practice);

        // Add new associations
        for (Integer specialityId : specialityIds) {
            Optional<Speciality> speciality = specialityRepository.findById(specialityId);
            speciality.ifPresent(s -> {
                Practice_Speciality practiceSpeciality = new Practice_Speciality();
                practiceSpeciality.setPractice(practice);
                practiceSpeciality.setSpeciality(s);
                practiceSpecialityRepository.save(practiceSpeciality);
            });
        }
    }

    private void updateDoctorPractices(Practice practice, List<Integer> doctorIds) {
        // Remove old associations
        doctorPracticeRepository.deleteAllByPractice(practice);

        // Add new associations
        for (Integer doctorId : doctorIds) {
            Optional<Doctor> doctor = doctorRepository.findById(doctorId);
            doctor.ifPresent(d -> {
                Doctor_Practice doctorPractice = new Doctor_Practice();
                doctorPractice.setPractice(practice);
                doctorPractice.setDoctor(d);
                doctorPracticeRepository.save(doctorPractice);
            });
        }
    }

    // Delete Practice from MySQL & Elasticsearch
//    public void deletePractice(int id) {
//        practiceRepository.deleteById(id);
//        searchIndexService.deletePracticeFromIndex(id); // Remove from Elasticsearch
//    }
}
