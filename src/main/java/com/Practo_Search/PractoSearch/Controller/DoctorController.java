package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.DoctorDTO;
import com.Practo_Search.PractoSearch.Service.DoctorService;
import com.Practo_Search.PractoSearch.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable int id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        if (doctorDTO != null) {
            return ResponseEntity.ok(doctorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable int id, @RequestBody Doctor updatedDoctor) {
        Doctor updated = doctorService.updateDoctor(id, updatedDoctor);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ✅ API to delete a doctor (Removes from MySQL & Elasticsearch)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
//        doctorService.deleteDoctor(id);
//        return ResponseEntity.noContent().build();
//    }
}
