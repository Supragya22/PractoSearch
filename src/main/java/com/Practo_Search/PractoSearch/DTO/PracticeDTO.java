package com.Practo_Search.PractoSearch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeDTO {
    private String name;
    private String address;
    private String state;
    private String city;
    private String website;
}
