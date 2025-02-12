package com.Practo_Search.PractoSearch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {
    private String type;
    private int id;
    private String name;
    private String additionalInfo;
}

