package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateUpdateStatus;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_UPDATE_STATUS)
public class CandidateUpdateStatusEventListener
    implements DomainEventListener<CandidateUpdateStatus> {

  private static final Logger LOG =
      LoggerFactory.getLogger(CandidateUpdateStatusEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public void onDomainEventConsumed(CandidateUpdateStatus message) throws Exception {
    LOG.info("consuming message from kafka : {}", new Object[] {message});
    CandidatePositionSolr exist = candidatePositionTemplate.queryForObject(
        new SimpleQuery(new SimpleStringCriteria("idCandidate:" + message.getIdCandidate()
            + " AND idPosition:" + message.getIdPosition())),
        CandidatePositionSolr.class);
    PartialUpdate update = new PartialUpdate("id", exist.getId());
    update.setValueOfField("status", message.getStatus());
    candidatePositionTemplate.saveBean(update);
    candidatePositionTemplate.commit();
  }
}
