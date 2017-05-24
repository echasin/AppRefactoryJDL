package com.innvo.repository.search;

import com.innvo.domain.Personpersonmbr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Personpersonmbr entity.
 */
public interface PersonpersonmbrSearchRepository extends ElasticsearchRepository<Personpersonmbr, Long> {
}
