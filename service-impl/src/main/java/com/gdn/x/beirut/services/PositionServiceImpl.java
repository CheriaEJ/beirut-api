package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

  @Autowired
  private PositionDAO positionDAO;

  @Override
  public List<Position> getAllPosition() {
    return positionDAO.findByMarkForDelete(false);
  }

  @Override
  public List<Position> getPositionByTitle(String title) {
    // TODO Auto-generated method stub
    return positionDAO.findByTitleContainingAndMarkForDelete(title, false);
  }

  public PositionDAO getPositionDao() {
    return this.positionDAO;
  }

  @Override
  @Transactional(readOnly = false)
  public boolean insertNewPosition(Position position) {
      this.getPositionDao().save(position);
      return true;
  }

  @Override
  @Transactional(readOnly = false)
  public void markForDeletePosition(List<String> ids) {
    List<Position> positions = new ArrayList<Position>();
    for (int i = 0; i < ids.size(); i++) {
      Position posi = this.getPositionDao().findByIdAndMarkForDelete(ids.get(i), false);
      Hibernate.initialize(posi.getCandidatePosition());
      for (CandidatePosition candpos : posi.getCandidatePosition()) {
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
    Position posi = this.getPositionDao().findByIdAndMarkForDelete(id, false);
    if (posi != null) {
      posi.setTitle(title);
      this.getPositionDao().save(posi);
      return true;
    }
    return false;
  }
}
