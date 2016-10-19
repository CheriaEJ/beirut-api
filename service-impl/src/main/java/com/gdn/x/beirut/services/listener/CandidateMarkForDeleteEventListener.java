package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateMarkForDelete;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_MARK_FOR_DELETE)
public class CandidateMarkForDeleteEventListener
    implements DomainEventListener<CandidateMarkForDelete> {
  private static final Logger LOG =
      LoggerFactory.getLogger(CandidateMarkForDeleteEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  public String STORE_ID = "STORE_ID:";

  public String AND = " AND ";

  @Override
  public void onDomainEventConsumed(CandidateMarkForDelete message) throws Exception {
    // System.out.println("sout consume...");
    LOG.info("%% CONSUMING MESSAGE FROM KAFKA (Mark for delete) : {}",
        new Object[] {message} + " %%");
    LOG.info(
        "%% QUERY = " + STORE_ID + message.getStoreId() + AND + "idCandidate:" + message.getId());
    Page<CandidatePositionSolr> candidatePositionSolrPage =
        this.candidatePositionTemplate.queryForPage(
            new SimpleQuery(
                STORE_ID + message.getStoreId() + AND + "idCandidate:" + message.getId()),
            CandidatePositionSolr.class);

    // candidatePositionTemplate.queryForPage(
    // new SimpleQuery(new SimpleStringCriteria(realQuery)).setPageRequest(pageable),
    // CandidatePositionSolr.class)

    LOG.info("%%QUERY DAPET : " + candidatePositionSolrPage.getNumberOfElements() + " %%");
    for (CandidatePositionSolr candidatePositionSolr : candidatePositionSolrPage) {
      candidatePositionSolr.setMarkForDelete(true);
    }
    LOG.info("%%DEBUG IZAL" + candidatePositionSolrPage.getContent().get(0).isMarkForDelete());
    this.candidatePositionTemplate.saveBeans(candidatePositionSolrPage.getContent());
    this.candidatePositionTemplate.commit();
  }
}
