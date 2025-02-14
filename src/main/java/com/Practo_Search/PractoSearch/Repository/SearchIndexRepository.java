package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.SearchIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchIndexRepository extends ElasticsearchRepository<SearchIndex, String> {
    List<SearchIndex> findByNameContainingIgnoreCase(String keyword);
}
