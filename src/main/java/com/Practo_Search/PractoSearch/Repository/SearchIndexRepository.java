package com.Practo_Search.PractoSearch.Repository;

import com.Practo_Search.PractoSearch.model.SearchIndex;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchIndexRepository extends ElasticsearchRepository<SearchIndex, String> {
    @Query("{\"bool\": {\"should\": [" +
            "{\"wildcard\": {\"name\": \"*?0*\"}}," +
            "{\"wildcard\": {\"additionalInfo\": \"*?0*\"}}," +
            "{\"wildcard\": {\"specialities\": \"*?0*\"}}," +
            "{\"match_phrase_prefix\": {\"name\": \"?0\"}}" +
            "]}}")
    List<SearchIndex> searchByKeyword(String keyword);
}
