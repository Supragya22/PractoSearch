package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.PracticeDTO;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.Service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/practices")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class PracticeController {

    @Autowired
    private PracticeService practiceService;

    // API to fetch Practice details by ID
    @GetMapping("/{id}")
    public ResponseEntity<PracticeDTO> getPracticeById(@PathVariable int id) {
        PracticeDTO practiceDTO = practiceService.getPracticeById(id);
        if (practiceDTO != null) {
            return ResponseEntity.ok(practiceDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
