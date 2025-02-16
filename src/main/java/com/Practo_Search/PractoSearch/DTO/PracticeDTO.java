package com.Practo_Search.PractoSearch.DTO;

import com.Practo_Search.PractoSearch.model.Doctor;
import com.Practo_Search.PractoSearch.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeDTO {
    private String name;
    private String address;
    private String state;
    private String city;
    private String website;
    private List<String> doctors;
    private List<String> specialities;
}
