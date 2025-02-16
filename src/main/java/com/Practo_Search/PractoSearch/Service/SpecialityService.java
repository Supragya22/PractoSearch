package com.Practo_Search.PractoSearch.Service;

import com.Practo_Search.PractoSearch.Repository.SpecialityRepository;
import com.Practo_Search.PractoSearch.model.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {

    @Autowired
    private SpecialityRepository specialityRepository;

    // Get all specialities
    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }
}
