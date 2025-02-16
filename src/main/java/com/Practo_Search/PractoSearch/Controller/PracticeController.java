package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.PracticeDTO;
import com.Practo_Search.PractoSearch.DTO.PracticeIdDTO;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.Service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/practices")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class PracticeController {

    @Autowired
    private PracticeService practiceService;

    // Get all doctors
    @GetMapping
    public List<Practice> getAll() {
        return practiceService.getAllPractices();
    }



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
    @PostMapping
    public ResponseEntity<PracticeIdDTO> savePractice(@RequestBody PracticeIdDTO practice) {
        PracticeIdDTO savedPractice = practiceService.savePractice(practice);
        return ResponseEntity.ok(savedPractice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PracticeIdDTO> updatePractice(@PathVariable int id, @RequestBody PracticeIdDTO updatedPractice) {
        PracticeIdDTO updated = practiceService.updatePractice(id, updatedPractice);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePractice(@PathVariable int id) {
//        practiceService.deletePractice(id);
//        return ResponseEntity.noContent().build();
//    }

}
