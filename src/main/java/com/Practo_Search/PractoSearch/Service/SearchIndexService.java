package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.model.SearchIndex;
import com.Practo_Search.PractoSearch.Repository.SearchIndexRepository;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SearchIndexService {

    @Autowired
    private SearchIndexRepository searchIndexRepository;

    // Index a Doctor
    public void indexDoctor(Doctor doctor) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("D-" + doctor.getId());
        searchIndex.setType("Doctor");
        searchIndex.setName(doctor.getName());
        searchIndex.setAdditionalInfo(doctor.getExperience() + " years experience");
        searchIndex.setSpecialities(doctor.getSpecialities().stream().map(Speciality::getName).collect(Collectors.toList()));

        searchIndexRepository.save(searchIndex);
    }

    // Index a Practice
    public void indexPractice(Practice practice) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("P-" + practice.getId());
        searchIndex.setType("Practice");
        searchIndex.setName(practice.getName());
        searchIndex.setAdditionalInfo(practice.getCity() + ", " + practice.getState());

        searchIndexRepository.save(searchIndex);
    }

    // Index a Speciality
    public void indexSpeciality(Speciality speciality) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.setId("S-" + speciality.getId());
        searchIndex.setType("Speciality");
        searchIndex.setName(speciality.getName());

        searchIndexRepository.save(searchIndex);
    }
}
