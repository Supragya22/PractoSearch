package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.PracticeIdDTO;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.SearchIndex;
import com.Practo_Search.PractoSearch.Repository.SearchIndexRepository;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchIndexService {

    @Autowired
    private SearchIndexRepository searchIndexRepository;

    public void indexDoctor(Doctor doctor) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("D-" + doctor.getId());
        searchIndex.setType("Doctor");
        searchIndex.setName(doctor.getName());
        searchIndex.setAdditionalInfo(doctor.getExperience() + " years experience");
        searchIndex.setSpecialities(doctor.getSpecialities() != null
                ? doctor.getSpecialities().stream().map(Speciality::getName).collect(Collectors.toList())
                : Collections.emptyList());

        searchIndexRepository.save(searchIndex);
    }
//public void indexDoctor(Doctor doctor) {
//    SearchIndex searchIndex = new SearchIndex();
//    searchIndex.setId("D-" + doctor.getId());
//    searchIndex.setType("Doctor");
//    searchIndex.setName(doctor.getName());
//    searchIndex.setAdditionalInfo(doctor.getExperience() + " years experience");
//
//    // Fetch Specialities via Doctor_Speciality mapping
//    List<String> specialities = doctor.getDoctorSpecialities() != null
//            ? doctor.getDoctorSpecialities().stream()
//            .map(ds -> ds.getSpeciality().getName()) // Extract Speciality names
//            .collect(Collectors.toList())
//            : Collections.emptyList();
//
//    searchIndex.setSpecialities(specialities);
//
//    searchIndexRepository.save(searchIndex);
//}


    public void indexPractice(Practice practice) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("P-" + practice.getId());
        searchIndex.setType("Practice");
        searchIndex.setName(practice.getName());
        searchIndex.setAdditionalInfo(practice.getCity() + ", " + practice.getState());
        searchIndex.setSpecialities(practice.getSpecialities() != null
                ? practice.getSpecialities().stream().map(Speciality::getName).collect(Collectors.toList())
                : Collections.emptyList());
        searchIndexRepository.save(searchIndex);
    }

//    public void indexPractice(PracticeIdDTO practice) {
//        SearchIndex searchIndex = new SearchIndex();
//        searchIndex.setId("P-" + practice.getId());
//        searchIndex.setType("Practice");
//        searchIndex.setName(practice.getName());
//        searchIndex.setAdditionalInfo(practice.getCity() + ", " + practice.getState());
//
//        // Fetch Specialities via Practice_Speciality mapping
//        List<String> specialities = practice.getPracticeSpecialities() != null
//                ? practice.getPracticeSpecialities().stream()
//                .map(ps -> ps.getSpeciality().getName()) // Extract Speciality names
//                .collect(Collectors.toList())
//                : Collections.emptyList();
//
//        searchIndex.setSpecialities(specialities);
//
//        searchIndexRepository.save(searchIndex);
//    }

    public void indexSpeciality(Speciality speciality) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("S-" + speciality.getId());
        searchIndex.setType("Speciality");
        searchIndex.setName(speciality.getName());

        searchIndexRepository.save(searchIndex);
    }
}
