package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.Repository.DoctorRepository;
import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
import com.Practo_Search.PractoSearch.Repository.SearchIndexRepository;
import com.Practo_Search.PractoSearch.model.SearchIndex;
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

        String processedKeyword = keyword.trim().replaceAll("[^a-zA-Z0-9 ]", ""); // Remove symbols

        if (processedKeyword.isEmpty()) {
            return results;
        }

        List<SearchIndex> esResults = searchIndexRepository.searchByKeyword(processedKeyword);

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

        return results;
    }

//    private List<SearchResultDTO> fallbackToMySQL(String keyword) {
//        List<SearchResultDTO> fallbackResults = new ArrayList<>();
//
//        List<Doctor> doctors = doctorRepository.searchByDoctorNameOrSpeciality(keyword);
//        for (Doctor doctor : doctors) {
//            List<String> specialities = doctor.getDoctorSpecialities()
//                    .stream()
//                    .map(ds -> ds.getSpeciality().getName())
//                    .toList();
//
//            fallbackResults.add(new SearchResultDTO(
//                    "Doctor",
//                    doctor.getId(),
//                    doctor.getName(),
//                    doctor.getExperience() + " years experience",
//                    specialities
//            ));
//        }
//
//        List<Practice> practices = practiceRepository.searchByPracticeNameOrCityOrStateOrSpeciality(keyword);
//        for (Practice practice : practices) {
//            fallbackResults.add(new SearchResultDTO(
//                    "Practice",
//                    practice.getId(),
//                    practice.getName(),
//                    practice.getCity() + ", " + practice.getState(),
//                    null
//            ));
//        }
//
//        return fallbackResults;
//    }
//    private void indexResultsToElasticsearch(List<SearchResultDTO> results) {
//        List<SearchIndex> searchIndexes = new ArrayList<>();
//        for (SearchResultDTO dto : results) {
//            String esId = dto.getType() + "-" + dto.getId(); // Unique ID format
//
//            searchIndexes.add(new SearchIndex(
//                    esId,
//                    dto.getType(),
//                    dto.getName(),
//                    dto.getAdditionalInfo(),
//                    dto.getSpecialities()
//            ));
//        }
//
//        searchIndexRepository.saveAll(searchIndexes);
//    }

    private Integer extractEntityId(String id) {
        try {
            return Integer.parseInt(id.split("-")[1]); // Extract numeric part
        } catch (Exception e) {
            return null;
        }
    }
}
