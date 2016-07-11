package com.gdn.x.beirut.solr.services;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public class CandidatePositionSolrTest {
  private static final Pageable DEFAULT_PAGEABLE = PageableHelper.generatePageable(0, 4);

  private static final String STORE_ID = "StoreID";

  private static final String FIRST_NAME = "FIRSTNAME";

  private static final String WILDCARD = "*";

  private static final String EMAIL_ADDRESS = "email@address.com";

  private static final String LAST_NAME = "LASTNAME";

  private static final String PHONE_NUMBER = "PHONENUMBER";

  private static final String STATUS = "APPLY";

  private static final String TITLE = "TITLE";


  @InjectMocks
  private CandidatePositionSolrServiceImpl candidatePositionSolrServiceImpl;

  @Mock
  private SolrTemplate candidatePositionTemplate;

  @Before
  public void initialize() {
    initMocks(this);
  }

  @Test
  public void testExecuteSolrQuery() {
    List<CandidatePositionSolr> candidatePositionSolrs = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      CandidatePositionSolr candidatePositionSolr = new CandidatePositionSolr();
      candidatePositionSolr.setCreatedDate(new Date());
      candidatePositionSolr.setEmailAddress(i + EMAIL_ADDRESS);
      candidatePositionSolr.setFirstName(i + " " + FIRST_NAME);
      candidatePositionSolr.setId(UUID.randomUUID().toString());
      candidatePositionSolr.setIdCandidate(UUID.randomUUID().toString());
      candidatePositionSolr.setIdPosition(UUID.randomUUID().toString());
      candidatePositionSolr.setLastName(i + LAST_NAME);
      candidatePositionSolr.setMarkForDelete(false);
      candidatePositionSolr.setPhoneNumber(i + PHONE_NUMBER);
      candidatePositionSolr.setStatus(STATUS);
      candidatePositionSolr.setTitle(TITLE);
      candidatePositionSolrs.add(candidatePositionSolr);
    }
    ScoredPage<CandidatePositionSolr> pageCandidatePositionSolrs = new SolrResultPage<>(
        candidatePositionSolrs, DEFAULT_PAGEABLE, candidatePositionSolrs.size(), 2.5f);
    Query query = new SimpleQuery(new SimpleStringCriteria("name:" + WILDCARD + FIRST_NAME))
        .setPageRequest(DEFAULT_PAGEABLE);
    Mockito.when(this.candidatePositionTemplate.queryForPage(query, CandidatePositionSolr.class))
        .thenReturn(pageCandidatePositionSolrs);
    candidatePositionSolrServiceImpl.executeSolrQuery("name:" + WILDCARD + FIRST_NAME, STORE_ID,
        DEFAULT_PAGEABLE);

    Mockito.verify(this.candidatePositionTemplate).queryForPage(Mockito.any(Query.class),
        Mockito.any());
  }
}
