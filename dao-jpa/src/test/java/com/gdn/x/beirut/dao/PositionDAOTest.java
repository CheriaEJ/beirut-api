package com.gdn.x.beirut.dao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.entities.Position;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class PositionDAOTest {
  private static final String AUTHOR = "unit-test";
  private static final String STORE_ID_1 = "store1";
  private static final String STORE_ID_2 = "store2";
  public static final String POSITION_TITLE_PREFIX = "position-";
  private List<Position> positions;

  @Autowired
  private PositionDAO positionDao;

  @Before
  public void initialize() {
    positions = new ArrayList<Position>();
    for (int i = 1; i <= 15; i++) {
      Position positionForStore1 = new Position();
      positionForStore1.setTitle(POSITION_TITLE_PREFIX + i);
      positionForStore1.setCreatedBy(AUTHOR);
      positionForStore1.setStoreId(STORE_ID_1);
      positions.add(positionForStore1);

      Position positionForStore2 = new Position();
      positionForStore2.setTitle(POSITION_TITLE_PREFIX + i);
      positionForStore2.setCreatedBy(AUTHOR);
      positionForStore2.setStoreId(STORE_ID_2);
      positions.add(positionForStore2);
    }
    this.positionDao.save(this.positions);
  }

  @Test
  public void testFindByIdAndStoreIdAndMarkForDelete() {
    List<Position> allPositionsStoreId1 =
        this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_1, false);
    Position positionToCheck = allPositionsStoreId1.get(0);
    assertTrue(this.positionDao.findByIdAndStoreIdAndMarkForDelete(positionToCheck.getId(),
        positionToCheck.getStoreId(), false).getCreatedBy().equals(AUTHOR));

    List<Position> allPositionsStoreId2 =
        this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_2, false);
    Position positionToCheck2 = allPositionsStoreId2.get(0);
    Assert.assertNull(this.positionDao.findByIdAndStoreIdAndMarkForDelete(positionToCheck.getId(),
        positionToCheck2.getStoreId(), false));
  }

  @Test
  public void testFindByStoreId() {
    assertTrue(!this.positionDao.findByStoreId(STORE_ID_1).isEmpty());
    assertTrue(this.positionDao.findByStoreId(STORE_ID_1).size() == 15);
    assertTrue(!this.positionDao.findByStoreId(STORE_ID_2).isEmpty());
    assertTrue(this.positionDao.findByStoreId(STORE_ID_2).size() == 15);
    assertTrue(this.positionDao.findByStoreId(STORE_ID_1, PageableHelper.generatePageable(0, 8))
        .getContent().size() == 8);
    assertTrue(this.positionDao.findByStoreId(STORE_ID_1, PageableHelper.generatePageable(1, 8))
        .getContent().size() == 7);
    assertTrue(this.positionDao.findByStoreId(STORE_ID_2, PageableHelper.generatePageable(0, 8))
        .getContent().size() == 8);
    assertTrue(this.positionDao.findByStoreId(STORE_ID_2, PageableHelper.generatePageable(1, 8))
        .getContent().size() == 7);
  }

  @Test
  public void testFindByStoreIdAndId() {
    List<Position> positions = this.positionDao.findByStoreId(STORE_ID_1);
    List<Position> positions2 = this.positionDao.findByStoreId(STORE_ID_2);
    assertTrue(this.positionDao.findByStoreIdAndId(STORE_ID_1, positions.get(0).getId()) != null);
    assertTrue(this.positionDao.findByStoreIdAndId(STORE_ID_1, positions2.get(0).getId()) == null);
  }

  @Test
  public void testFindByStoreIdMarkForDelete() {
    assertTrue(this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_1, true).isEmpty());
    assertTrue(this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_1, false).size() == 15);
    assertTrue(this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_2, true).isEmpty());
    assertTrue(this.positionDao.findByStoreIdAndMarkForDelete(STORE_ID_2, false).size() == 15);
  }

  @Test
  public void testFindByTitleContainingAndStoreIdAndMarkForDelete() {
    assertTrue(this.positionDao
        .findByTitleContainingAndStoreIdAndMarkForDelete("1", STORE_ID_1, false).size() == 7);
    assertTrue(this.positionDao
        .findByTitleContainingAndStoreIdAndMarkForDelete("2", STORE_ID_2, false).size() == 2);
  }

  @Test
  public void testGetAllPositionByStoreId() {
    assertTrue(this.positionDao.getAllPositionByStoreId(STORE_ID_1).size() > 0);
    assertTrue(this.positionDao.getAllPositionByStoreId(STORE_ID_2).size() > 0);
  }

}
