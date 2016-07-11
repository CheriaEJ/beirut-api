package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateUpdateInformation;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_UPDATE_INFORMATION)
public class CandidateUpdateInformationEventListener
    implements DomainEventListener<CandidateUpdateInformation> {

  private static final Logger LOG =
      LoggerFactory.getLogger(CandidateUpdateInformationEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public void onDomainEventConsumed(CandidateUpdateInformation message) throws Exception {
    LOG.info("consuming message from kafka : " + message.toString());
    Page<CandidatePositionSolr> result = candidatePositionTemplate.queryForPage(
        new SimpleQuery(new SimpleStringCriteria(
            "idCandidate:" + message.getIdCandidate() + " AND STORE_ID:" + message.getStoreId())),
        CandidatePositionSolr.class);
    for (CandidatePositionSolr candidatePositionSolr : result.getContent()) {
      candidatePositionSolr.setFirstName(message.getFirstName());
      candidatePositionSolr.setLastName(message.getLastName());
      candidatePositionSolr.setEmailAddress(message.getEmailAddress());
      candidatePositionSolr.setPhoneNumber(message.getPhoneNumber());
      candidatePositionTemplate.saveBean(candidatePositionSolr);
      candidatePositionTemplate.commit();
    }
  }

}
