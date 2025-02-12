package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.DTO.PracticeDTO;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.Repository.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;

    public PracticeDTO getPracticeById(int id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        return practice.map(p -> new PracticeDTO(p.getName(), p.getAddress(), p.getState(), p.getCity(), p.getWebsite())).orElse(null);
    }
}
