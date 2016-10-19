package com.gdn.x.beirut.solr.services;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service(value = "candidatePositionSolrService")
public class CandidatePositionSolrServiceImpl implements CandidatePositionSolrService {

  private static final String STORE_ID = "STORE_ID:";
  private static final String AND = " AND ";
  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public Page<CandidatePositionSolr> executeSolrQuery(String query, String storeId,
      Pageable pageable) {
    String realQuery = STORE_ID + storeId + AND + query;
    return candidatePositionTemplate.queryForPage(
        new SimpleQuery(new SimpleStringCriteria(realQuery)).setPageRequest(pageable),
        CandidatePositionSolr.class);
    // return candidatePositionTemplate.queryForPage(new SimpleQuery() , clazz);
  }
}
