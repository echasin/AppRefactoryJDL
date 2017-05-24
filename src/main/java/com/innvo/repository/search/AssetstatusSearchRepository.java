package com.innvo.repository.search;

import com.innvo.domain.Assetstatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assetstatus entity.
 */
public interface AssetstatusSearchRepository extends ElasticsearchRepository<Assetstatus, Long> {
}
