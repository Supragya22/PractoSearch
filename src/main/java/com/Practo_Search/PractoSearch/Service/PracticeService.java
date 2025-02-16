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
import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private SearchIndexService searchIndexService; // Manages Elasticsearch syncing

    // Fetch practice details from MySQL
    public PracticeDTO getPracticeById(int id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        return practice.map(p -> new PracticeDTO(
                p.getName(),
                p.getAddress(),
                p.getState(),
                p.getCity(),
                p.getWebsite(),
                p.getDoctors()!= null ? p.getDoctors().stream().map(Doctor::getName).toList() : Collections.emptyList(),
                p.getSpecialities()!= null ? p.getSpecialities().stream().map(Speciality::getName).toList() : Collections.emptyList()
        )).orElse(null);
    }

    // Save Practice in MySQL & sync to Elasticsearch
    public Practice savePractice(Practice practice) {
        Practice savedPractice = practiceRepository.save(practice);
        searchIndexService.indexPractice(savedPractice); // Sync with Elasticsearch
        return savedPractice;
    }

    // Update Practice & sync changes to Elasticsearch
    public Practice updatePractice(int id, Practice updatedPractice) {
        Optional<Practice> existingPracticeOpt = practiceRepository.findById(id);
        if (existingPracticeOpt.isPresent()) {
            Practice existingPractice = existingPracticeOpt.get();
            existingPractice.setName(updatedPractice.getName());
            existingPractice.setAddress(updatedPractice.getAddress());
            existingPractice.setState(updatedPractice.getState());
            existingPractice.setCity(updatedPractice.getCity());
            existingPractice.setWebsite(updatedPractice.getWebsite());
            existingPractice.setDoctors(updatedPractice.getDoctors());
            existingPractice.setSpecialities(updatedPractice.getSpecialities());

            Practice savedPractice = practiceRepository.save(existingPractice);
            searchIndexService.indexPractice(savedPractice); // Update Elasticsearch
            return savedPractice;
        }
        return null;
    }

    // Delete Practice from MySQL & Elasticsearch
//    public void deletePractice(int id) {
//        practiceRepository.deleteById(id);
//        searchIndexService.deletePracticeFromIndex(id); // Remove from Elasticsearch
//    }
}
