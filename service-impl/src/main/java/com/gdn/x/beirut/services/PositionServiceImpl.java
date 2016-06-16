package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

  private static final Logger LOG = LoggerFactory.getLogger(PositionServiceImpl.class);

  @Autowired
  private PositionDAO positionDAO;

  @Override
  public List<Position> getAllPosition() {
    return positionDAO.findAll();
  }

  @Override
  public Page<Position> getAllPositionWithPageable(Pageable pageable) {
    return positionDAO.findAll(pageable);
  }

  @Override
  public Position getPosition(String positionId) throws Exception {
    Position existingPosition = getPositionDao().findOne(positionId);
    if (existingPosition == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
          "no position id = " + positionId);
    }
    return existingPosition;
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

    Set<CandidatePosition> candidatePositions = position.getCandidatePositions();
    for (CandidatePosition candidatePosition : candidatePositions) {
      Hibernate.initialize(candidatePosition.getCandidate());
    }
    return position;
  }

  @Override
  @Transactional(readOnly = false)
  public boolean insertNewPosition(Position position) {
    for (CandidatePosition iterable_element : position.getCandidatePositions()) {
      iterable_element.setPosition(position);
    }
    this.getPositionDao().save(position);
    return true;
  }

  @Override
  @Transactional(readOnly = false)
  public void markForDeletePosition(List<String> ids) {
    System.out.println(ids.toString());
    List<Position> positions = new ArrayList<Position>();
    for (int i = 0; i < ids.size(); i++) {
      Position posi =
          (Position) this.getPositionDao().findByStoreIdAndMarkForDelete(ids.get(i), false);
      Hibernate.initialize(posi.getCandidatePositions());
      Iterator<CandidatePosition> iterator = posi.getCandidatePositions().iterator();
      while (iterator.hasNext()) {
        CandidatePosition candpos = iterator.next();
        candpos.setMarkForDelete(true);
      }
      posi.setMarkForDelete(true);
      positions.add(posi);
    }
    this.getPositionDao().save(positions);
  }

  @Override
  @Transactional(readOnly = false)
  public boolean updatePositionTitle(String id, String title) {
    Position posi = this.positionDAO.findOne(id);
    if (posi != null) {
      posi.setTitle(title);
      this.positionDAO.save(posi);
      return true;
    } else {
      return false;
    }
  }
}
