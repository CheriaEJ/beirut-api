package com.gdn.x.beirut.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Position;

public interface PositionDAO extends JpaRepository<Position, String> {

  Position findByIdAndMarkForDelete(String id, boolean bool);

  List<Position> findByMarkForDelete(boolean bool);
  // @Query(value = "SELECT id FROM CandidatePosition WHERE id = ?1 and markForDelete = ?2",
  // nativeQuery = true)
  // List<CandidatePosition> findByIDAndMarkForDelete(String id, boolean bool);
  //
  // @Query(value = "SELECT position_id FROM CandidatePosition WHERE position_id = ?1 and
  // markForDelete = ?2", nativeQuery = true)
  // List<String> findByPositionIDAndMarkForDelete(String id, boolean bool);

  List<Position> findByTitleAndMarkForDeleteNot(String title, boolean bool);

  List<Position> findByTitleContainingAndMarkForDelete(String title, boolean bool);

}
