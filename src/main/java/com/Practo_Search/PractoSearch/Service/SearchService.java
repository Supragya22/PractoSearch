//package com.Practo_Search.PractoSearch.Service;
//
//import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
//import com.Practo_Search.PractoSearch.model.Doctor;
//import com.Practo_Search.PractoSearch.model.Practice;
//import com.Practo_Search.PractoSearch.model.Speciality;
//import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
//import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
//import com.Practo_Search.PractoSearch.Repository.SpecialityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class SearchService {
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @Autowired
//    private SpecialityRepository specialityRepository;
//
//    @Autowired
//    private PracticeRepository practiceRepository;
//
//    public List<SearchResultDTO> universalSearch(String keyword) {
//        List<SearchResultDTO> results = new ArrayList<>();
//
//        // Search Doctors by name or speciality
//        List<Doctor> doctors = doctorRepository.searchByDoctorNameOrSpeciality(keyword);
//        for (Doctor doctor : doctors) {
//            List<String> specialities = doctor.getSpecialities()
//                    .stream()
//                    .map(Speciality::getName) // Extract speciality names
//                    .toList();
//
//            results.add(new SearchResultDTO("Doctor", doctor.getId(), doctor.getName(), doctor.getExperience() + " years experience", specialities));
//        }
//
//
//        // Search Practices by name, city, or speciality
//        List<Practice> practices = practiceRepository.searchByPracticeNameOrCityOrStateOrSpeciality(keyword);
//        for (Practice practice : practices) {
//            results.add(new SearchResultDTO("Practice", practice.getId(), practice.getName(), practice.getCity() + ", " + practice.getState(), null));
//        }
//
//        return results;
//    }
//}


package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
import com.Practo_Search.PractoSearch.Repository.SearchIndexRepository;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.SearchIndex;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchIndexRepository searchIndexRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    public List<SearchResultDTO> universalSearch(String keyword) {
        List<SearchResultDTO> results = new ArrayList<>();

        // ✅ Step 1: Try searching in Elasticsearch first
        List<SearchIndex> esResults = searchIndexRepository.findByNameContainingIgnoreCase(keyword);
        for (SearchIndex result : esResults) {
            Integer entityId = extractEntityId(result.getId());

            if (entityId != null) {
                results.add(new SearchResultDTO(
                        result.getType(),
                        entityId,
                        result.getName(),
                        result.getAdditionalInfo(),
                        result.getSpecialities()
                ));
            }
        }

        // ✅ Step 2: If Elasticsearch is empty, fallback to MySQL
        if (results.isEmpty()) {
            List<SearchResultDTO> fallbackResults = fallbackToMySQL(keyword);
            results.addAll(fallbackResults);

            // ✅ Step 3: Sync MySQL results to Elasticsearch for future searches
            if (!fallbackResults.isEmpty()) {
                indexResultsToElasticsearch(fallbackResults);
            }
        }

        return results;
    }

    private List<SearchResultDTO> fallbackToMySQL(String keyword) {
        List<SearchResultDTO> fallbackResults = new ArrayList<>();

        // 🔍 Search in MySQL for Doctors
        List<Doctor> doctors = doctorRepository.searchByDoctorNameOrSpeciality(keyword);
        for (Doctor doctor : doctors) {
            List<String> specialities = doctor.getSpecialities()
                    .stream()
                    .map(Speciality::getName)
                    .toList();

            fallbackResults.add(new SearchResultDTO(
                    "Doctor",
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getExperience() + " years experience",
                    specialities
            ));
        }

        // 🔍 Search in MySQL for Practices
        List<Practice> practices = practiceRepository.searchByPracticeNameOrCityOrStateOrSpeciality(keyword);
        for (Practice practice : practices) {
            fallbackResults.add(new SearchResultDTO(
                    "Practice",
                    practice.getId(),
                    practice.getName(),
                    practice.getCity() + ", " + practice.getState(),
                    null
            ));
        }

        return fallbackResults;
    }

    // ✅ Sync new MySQL results to Elasticsearch
    private void indexResultsToElasticsearch(List<SearchResultDTO> results) {
        List<SearchIndex> searchIndexes = new ArrayList<>();
        for (SearchResultDTO dto : results) {
            String esId = dto.getType() + "-" + dto.getId(); // Unique ID format

            searchIndexes.add(new SearchIndex(
                    esId,
                    dto.getType(),
                    dto.getName(),
                    dto.getAdditionalInfo(),
                    dto.getSpecialities()
            ));
        }

        searchIndexRepository.saveAll(searchIndexes); // Bulk insert to Elasticsearch
    }

    private Integer extractEntityId(String id) {
        try {
            return Integer.parseInt(id.split("-")[1]); // Extract numeric part
        } catch (Exception e) {
            return null;
        }
    }
}
