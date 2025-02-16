package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.sql.results.LoadingLogger.LOGGER;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResultDTO>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<SearchResultDTO> results = searchService.universalSearch(keyword);
        return ResponseEntity.ok(results);
    }
}

