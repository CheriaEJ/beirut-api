package com.gdn.x.beirut.services.listener;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.domain.event.model.ApplyNewPosition;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_APPLY_NEW_POSITION)
public class CandidateApplyNewPositionEventListener
    implements DomainEventListener<ApplyNewPosition> {

  private static final Logger LOG =
      LoggerFactory.getLogger(CandidateApplyNewPositionEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Autowired
  private GdnMapper gdnMapper;

  @Override
  public void onDomainEventConsumed(ApplyNewPosition message) throws Exception {
    LOG.info("consuming message from kafka : {}", new Object[] {message});
    ScoredPage<CandidatePositionSolr> existingSolrData = candidatePositionTemplate.queryForPage(
        new SimpleQuery(
            new SimpleStringCriteria("STORE_ID:" + message.getStoreId() + " AND idCandidate:"
                + message.getIdCandidate() + " AND idPosition:" + message.getIdPosition())),
        CandidatePositionSolr.class);
    if (existingSolrData.getContent().size() != 0) {
      throw new ApplicationException(ErrorCategory.UNSPECIFIED, "Data Already Existed");
    }


    CandidatePositionSolr newCandidateSolr =
        gdnMapper.deepCopy(message, CandidatePositionSolr.class);
    newCandidateSolr.setId(UUID.randomUUID().toString());
    candidatePositionTemplate.saveBean(newCandidateSolr);
    candidatePositionTemplate.commit();
  }

}
