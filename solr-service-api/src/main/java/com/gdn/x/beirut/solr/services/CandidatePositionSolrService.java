package com.gdn.x.beirut.solr.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public interface CandidatePositionSolrService {

  public Page<CandidatePositionSolr> executeSolrQuery(String query, String storeId,
      Pageable pageable);

}
