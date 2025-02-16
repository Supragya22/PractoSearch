package com.Practo_Search.PractoSearch.DTO;

import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Practice;
import com.Practo_Search.PractoSearch.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeIdDTO {
    private String name;
    private String address;
    private String state;
    private String city;
    private String website;
    private List<Integer> doctorIds;
    private List<Integer> specialityIds;

    public PracticeIdDTO(Practice practice) {
        this.name = practice.getName();
        this.address = practice.getAddress();
        this.state = practice.getState();
        this.city = practice.getCity();
        this.city = practice.getWebsite();

        this.specialityIds = practice.getPracticeSpecialities().stream()
                .filter(ds -> ds.getSpeciality() != null)
                .map(ds -> ds.getSpeciality().getId())
                .collect(Collectors.toList());

        this.doctorIds = practice.getDoctorPractices().stream()
                .filter(dp -> dp.getPractice() != null)
                .map(dp -> dp.getPractice().getId())
                .collect(Collectors.toList());
    }
}
