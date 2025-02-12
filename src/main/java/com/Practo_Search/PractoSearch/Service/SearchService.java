package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
import com.Practo_Search.PractoSearch.Repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    public List<SearchResultDTO> universalSearch(String keyword) {
        List<SearchResultDTO> results = new ArrayList<>();

        // Search Doctors by name or speciality
        List<Doctor> doctors = doctorRepository.searchByDoctorNameOrSpeciality(keyword);
        for (Doctor doctor : doctors) {
            results.add(new SearchResultDTO("Doctor", doctor.getId(), doctor.getName(), doctor.getExperience() + " years experience"));
        }

        // Search Specialities
        List<Speciality> specialities = specialityRepository.searchBySpecialityName(keyword);
        for (Speciality speciality : specialities) {
            results.add(new SearchResultDTO("Speciality", speciality.getId(), speciality.getName(), "Medical Speciality"));
        }

        // Search Practices by name, city, or speciality
        List<Practice> practices = practiceRepository.searchByPracticeNameOrCityOrStateOrSpeciality(keyword);
        for (Practice practice : practices) {
            results.add(new SearchResultDTO("Practice", practice.getId(), practice.getName(), practice.getCity() + ", " + practice.getState()));
        }

        return results;
    }
}
