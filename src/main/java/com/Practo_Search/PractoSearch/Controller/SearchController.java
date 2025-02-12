package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResultDTO>> search(@RequestParam String keyword) {
        List<SearchResultDTO> results = searchService.universalSearch(keyword);
        return ResponseEntity.ok(results);
    }
}

