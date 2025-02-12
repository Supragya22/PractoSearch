package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
import com.Practo_Search.PractoSearch.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // API to fetch doctor details by ID (Excluding ID in response)
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable int id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        if (doctorDTO != null) {
            return ResponseEntity.ok(doctorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
