package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.base.domainevent.publisher.DomainEventPublisher;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionMarkForDelete;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

  private static final Logger LOG = LoggerFactory.getLogger(PositionServiceImpl.class);
  @Autowired
  private PositionDAO positionDAO;

  @Autowired
  private DomainEventPublisher domainEventPublisher;

  @Autowired
  private GdnMapper gdnMapper;

  @Override
  @Deprecated
  public List<Position> getAllPositionByStoreId(String storeId) {
    return positionDAO.findByStoreId(storeId);
  }

  @Override
  public Page<Position> getAllPositionByStoreIdWithPageable(String storeId, Pageable pageable) {
    return positionDAO.findByStoreId(storeId, pageable);
  }

  @Override
  public Position getPosition(String storeId, String positionId) throws Exception {
    Position existingPosition = getPositionDao().findByStoreIdAndId(storeId, positionId);
    if (existingPosition == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
          "no position id = " + positionId);
    }
    return existingPosition;
  }

  @Override
  public List<Position> getPositionByIds(List<String> positionIds) {
    return this.positionDAO.findAll(positionIds);
  }

  @Override
  public List<Position> getPositionByStoreIdAndMarkForDelete(String storeId,
      boolean markForDelete) {
    return this.positionDAO.findByStoreIdAndMarkForDelete(storeId, markForDelete);
  }

  @Override
  public List<Position> getPositionByTitle(String title, String storeId) {
    return positionDAO.findByTitleContainingAndStoreIdAndMarkForDelete(title, storeId, false);
  }

  public PositionDAO getPositionDao() {
    return this.positionDAO;
  }

  @Override
  public Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception {
    Position position = this.positionDAO.findByIdAndStoreIdAndMarkForDelete(id, storeId, false);
    if (position == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
          "no such id = " + id + " and storeId = " + storeId);
    }
    Hibernate.initialize(position.getCandidatePositions());

    List<CandidatePosition> candidatePositions = position.getCandidatePositions();
    for (CandidatePosition candidatePosition : candidatePositions) {
      Hibernate.initialize(candidatePosition.getCandidate());
    }
    return position;
  }

  @Override
  @Transactional(readOnly = false)
  public Position insertNewPosition(Position position) {
    for (CandidatePosition iterable_element : position.getCandidatePositions()) {
      iterable_element.setPosition(position);
    }
    return this.getPositionDao().save(position);
  }

  @Override
  @Transactional(readOnly = false)
  public void markForDeletePosition(String storeId, List<String> ids) throws Exception {
    // System.out.println(ids.toString());
    List<Position> positions = new ArrayList<Position>();
    for (int i = 0; i < ids.size(); i++) {
      Position position = this.positionDAO.findOne(ids.get(i));
      if (!position.getStoreId().equals(storeId)) {
        throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
            "position exist but storeId not match");
      }
      Hibernate.initialize(position.getCandidatePositions());
      Iterator<CandidatePosition> iterator = position.getCandidatePositions().iterator();
      while (iterator.hasNext()) {
        CandidatePosition candpos = iterator.next();
        candpos.setMarkForDelete(true);
      }
      position.setMarkForDelete(true);
      positions.add(position);
    }
    try {
      this.getPositionDao().save(positions);
      for (Position position : positions) {
        PositionMarkForDelete objectToPublish =
            gdnMapper.deepCopy(position, PositionMarkForDelete.class);
        domainEventPublisher.publish(objectToPublish, DomainEventName.POSITION_MARK_FOR_DELETE,
            PositionMarkForDelete.class);
      }
    } catch (RuntimeException e) {
      throw e;
    }
  }

  @Override
  @Transactional(readOnly = false)
  public boolean updatePositionTitle(String storeId, String id, String title) throws Exception {
    Position posi = this.positionDAO.findOne(id);
    if (posi != null) {
      if (!posi.getStoreId().equals(storeId)) {
        throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
            "position exist but storeId not match");
      }
      posi.setTitle(title);
      this.getPositionDao().save(posi);
      return true;
    }
    return false;
  }
}
