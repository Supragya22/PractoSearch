package com.Practo_Search.PractoSearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Document(indexName = "practo_search_index")
@AllArgsConstructor
@NoArgsConstructor
public class SearchIndex {

    @Id
    private String id; // Unique identifier
    private String type; // "Doctor", "Practice", or "Speciality"
    private String name;
    private String additionalInfo;
    private List<String> specialities;
}
