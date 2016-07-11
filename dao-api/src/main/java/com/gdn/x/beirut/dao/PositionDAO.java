package com.gdn.x.beirut.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Position;

public interface PositionDAO extends JpaRepository<Position, String> {
  Position findByIdAndStoreIdAndMarkForDelete(String id, String storeId, boolean markForDelete);

  List<Position> findByStoreId(String storeId);

  Page<Position> findByStoreId(String storeId, Pageable pageable);

  Position findByStoreIdAndId(String storeId, String id);

  List<Position> findByStoreIdAndMarkForDelete(String storeId, boolean bool);

  List<Position> findByTitleContainingAndStoreIdAndMarkForDelete(String title, String storeId,
      boolean bool);

  List<Position> getAllPositionByStoreId(String storeId);
}
