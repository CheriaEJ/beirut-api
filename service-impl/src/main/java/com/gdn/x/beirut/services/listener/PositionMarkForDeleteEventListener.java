package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionMarkForDelete;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.POSITION_MARK_FOR_DELETE)
public class PositionMarkForDeleteEventListener
    implements DomainEventListener<PositionMarkForDelete> {

  private static final Logger LOG = LoggerFactory.getLogger(PositionMarkForDelete.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public void onDomainEventConsumed(PositionMarkForDelete message) throws Exception {
    LOG.info("consuming message from kafka POPOP : {}", new Object[] {message});
    ScoredPage<CandidatePositionSolr> exist = candidatePositionTemplate.queryForPage(
        new SimpleQuery(new SimpleStringCriteria("idPosition:" + message.getId())),
        CandidatePositionSolr.class);
    for (CandidatePositionSolr candidatePositionSolr : exist.getContent()) {
      LOG.info("consuming message from kafka IDNYA WOOOI: {}", candidatePositionSolr.toString());
      PartialUpdate update = new PartialUpdate("id", candidatePositionSolr.getId());
      update.setValueOfField(CandidatePositionSolr.MARK_FOR_DELETE, true);
      candidatePositionTemplate.saveBean(update);
      candidatePositionTemplate.commit();
    }
  }

}
