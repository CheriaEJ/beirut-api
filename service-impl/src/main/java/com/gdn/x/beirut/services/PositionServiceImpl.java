package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.Iterator;
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
    for (CandidatePosition iterable_element : position.getCandidatePosition()) {
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
      Position posi = this.getPositionDao().findByIdAndMarkForDelete(ids.get(i), false);
      System.out.println("auibiubr");
      // hibernate initialize kayak gak kepanggil.
      Hibernate.initialize(posi.getCandidatePosition());
      System.out.println("uhoiaafubrugb");
      Iterator<CandidatePosition> iterator = posi.getCandidatePosition().iterator();
      while (iterator.hasNext()) {
        CandidatePosition candpos = iterator.next();
        candpos.setMarkForDelete(true);
        System.out.println("aaa" + candpos.isMarkForDelete());
      }
      posi.setMarkForDelete(true);
      positions.add(posi);
      System.out.println("patricia" + posi.isMarkForDelete() + " " + posi.getTitle());
    }
    this.getPositionDao().save(positions);
  }

  @Override
  @Transactional(readOnly = false)
  public boolean updatePositionTitle(String id, String title) {
    Position posi = this.positionDAO.findByIdAndMarkForDelete(id, false);
    if (posi != null) {
      posi.setTitle(title);
      this.positionDAO.save(posi);
      return true;
    } else {
      return false;
    }
  }
}
