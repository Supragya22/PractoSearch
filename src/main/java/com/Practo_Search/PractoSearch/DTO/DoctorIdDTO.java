package com.Practo_Search.PractoSearch.DTO;

import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorIdDTO {
    private String name;
    private int experience;
    private String qualifications;
    private List<Integer> specialityIds;
    private List<Integer> practiceIds;

    public DoctorIdDTO(Doctor doctor) {
        this.name = doctor.getName();
        this.experience = doctor.getExperience();
        this.qualifications = doctor.getQualifications();
        this.specialityIds = doctor.getDoctorSpecialities() != null
                ? doctor.getDoctorSpecialities().stream()
                .map(ds -> ds.getSpeciality().getId())
                .collect(Collectors.toList())
                : new ArrayList<>(); // Return an empty list if doctorSpecialities is null
        this.practiceIds = doctor.getDoctorPractices() != null
                ? doctor.getDoctorPractices().stream()
                .map(dp -> dp.getPractice().getId())
                .collect(Collectors.toList())
                : new ArrayList<>(); // Return an empty list if doctorPractices is null
    }



}
