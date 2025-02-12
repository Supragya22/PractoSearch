package com.Practo_Search.PractoSearch.Controller;

import com.Practo_Search.PractoSearch.DTO.SearchResultDTO;
import com.Practo_Search.PractoSearch.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Allow cross-origin requests
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public List<SearchResultDTO> search(@RequestParam String keyword) {
        return searchService.universalSearch(keyword);
    }
}

