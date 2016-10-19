package com.gdn.x.beirut.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.gdn.common.base.domainevent.publisher.DomainEventPublisher;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.PositionDescription;

public class PositionServiceTest {

  private static final String DEFAULT_ID = "ID";

  private static final String STORE_ID = "STORE_ID";

  @Mock
  private PositionDAO repository;

  @Mock
  private DomainEventPublisher domainEventPublisher;

  @InjectMocks
  private PositionServiceImpl service;

  private Position position;

  private final List<Position> positions = new ArrayList<Position>();

  private Position positionWithDescription;

  private PositionDescription positionDescription;

  private GdnMapper gdnMapper;


  // @Test
  // @Ignore
  // public void checkMarkForDelete() {
  // List<String> ids = new ArrayList<String>();
  // for (int i = 0; i < pos.size(); i++) {
  // ids.add(pos.get(i).getId());
  // }
  // this.service.markForDeletePosition(ids);
  // Mockito.verify(this.repository).findByIdAndMarkForDelete(this.position.getId(), false);
  // }

  @Before
  public void initialize() throws Exception {
    this.gdnMapper = new GdnMapper() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> T deepCopy(Object source, Class<T> destinationClass) {
        Mapper mapper = new DozerBeanMapper();
        T destination;
        try {
          destination = destinationClass.newInstance();
        } catch (InstantiationException e) {
          return (T) source;
        } catch (IllegalAccessException e) {
          return (T) source;
        }
        mapper.map(source, destination);
        return destination;
      }
    };
    initMocks(this);
    this.position = new Position();
    this.position.setTitle("Choa");
    this.position.setId("122");
    this.position.setStoreId("Store");
    positions.add(this.position);
    this.repository.save(this.position);
    Mockito.when(this.repository
        .findByTitleContainingAndStoreIdAndMarkForDelete(this.position.getTitle(), "Store", false))
        .thenReturn(positions);
    Mockito.when(this.repository.save(this.position)).thenReturn(this.position);
    List<String> aa = new ArrayList<String>();
    aa.add("1");
    positionDescription = new PositionDescription();
    this.positionWithDescription = new Position();
    this.positionWithDescription.setId(DEFAULT_ID);
    this.positionWithDescription.setStoreId(STORE_ID);
    this.positionWithDescription.setPositionDescription(this.positionDescription);

  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGetAllPositionByStoreId() {
    this.service.getAllPositionByStoreId(STORE_ID);
    verify(this.repository, Mockito.times(1)).findByStoreId(STORE_ID);
  }

  @Test
  public void testGetPosition() throws Exception {
    Mockito.when(repository.findByStoreIdAndId(STORE_ID, DEFAULT_ID)).thenReturn(position);
    this.service.getPosition(STORE_ID, DEFAULT_ID);
    verify(this.repository).findByStoreIdAndId(STORE_ID, DEFAULT_ID);
  }

  @Test
  public void testGetPositionAndReturnException() throws Exception {
    Mockito.when(repository.findByStoreIdAndId(STORE_ID, DEFAULT_ID)).thenReturn(null);
    try {
      this.service.getPosition(STORE_ID, DEFAULT_ID);
    } catch (Exception e) {
      if (e instanceof ApplicationException) {
        Assert.assertEquals("Can not find data :no position id = " + DEFAULT_ID, e.getMessage());
      } else {
        Assert.assertTrue(false);
      }
    }
    verify(this.repository).findByStoreIdAndId(STORE_ID, DEFAULT_ID);
  }

  @Test
  public void testGetPositionByIds() {
    Mockito.when(this.repository.findByStoreIdAndId(STORE_ID, DEFAULT_ID)).thenReturn(position);
    Assert
        .assertTrue(this.service.getPositionByStoreIdAndId(STORE_ID, DEFAULT_ID).equals(position));
    this.service.getPositionByStoreIdAndId(STORE_ID, DEFAULT_ID);
    Mockito.verify(this.repository, Mockito.times(2)).findByStoreIdAndId(STORE_ID, DEFAULT_ID);
  }

  @Test
  public void testGetPositionByStoreIdAndMarkForDelete() {
    Position position1 = new Position();
    position1.setId(DEFAULT_ID);
    position1.setStoreId(STORE_ID);
    Position position2 = new Position();
    position2.setId(DEFAULT_ID);
    position2.setStoreId(STORE_ID);
    List<String> positionIds = new ArrayList<String>();
    positionIds.add(position1.getId());
    positionIds.add(position2.getId());
    List<Position> positions = new ArrayList<Position>();
    positions.add(position1);
    positions.add(position2);
    Mockito.when(this.repository.findByStoreIdAndMarkForDelete(STORE_ID, true))
        .thenReturn(positions);
    Assert
        .assertTrue(this.service.getPositionByStoreIdAndMarkForDelete(STORE_ID, true) == positions);
    this.service.getPositionByStoreIdAndMarkForDelete(STORE_ID, true);
    Mockito.verify(this.repository, Mockito.times(2)).findByStoreIdAndMarkForDelete(STORE_ID, true);
  }

  @Test
  public void testGetPositionByTitle() {
    Mockito.when(
        this.repository.findByTitleContainingAndStoreIdAndMarkForDelete("TITLE", STORE_ID, false))
        .thenReturn(this.positions);
    Assert.assertTrue(this.service.getPositionByTitle("TITLE", STORE_ID) == this.positions);
    this.service.getPositionByTitle("TITLE", STORE_ID);
    Mockito.verify(this.repository, Mockito.times(2))
        .findByTitleContainingAndStoreIdAndMarkForDelete("TITLE", STORE_ID, false);
  }

  @Test
  public void testGetPositionDescriptionAndStoreId() throws Exception {
    when(this.repository.findByStoreIdAndId(STORE_ID, DEFAULT_ID))
        .thenReturn(this.positionWithDescription);
    // Black Box Test
    assertTrue(this.service.getPositionDescriptionAndStoreId(DEFAULT_ID,
        STORE_ID) == this.positionDescription);
    // White Box Test
    this.service.getPositionDescriptionAndStoreId(DEFAULT_ID, STORE_ID);
    verify(this.repository, times(2)).findByStoreIdAndId(STORE_ID, DEFAULT_ID);
  }

  @Test
  public void testGetPositionDetailByIdAndStoreId() throws Exception {
    Position shouldBeReturned = new Position();
    shouldBeReturned.setId(DEFAULT_ID);
    shouldBeReturned.setStoreId(STORE_ID);
    shouldBeReturned.setCreatedBy("dummy");
    shouldBeReturned.setMarkForDelete(false);
    shouldBeReturned.setTitle("This is a dummy");
    Mockito.when(repository.findByIdAndStoreIdAndMarkForDelete(DEFAULT_ID, STORE_ID, false))
        .thenReturn(shouldBeReturned);
    Position result = this.service.getPositionDetailByIdAndStoreId(DEFAULT_ID, STORE_ID);
    Mockito.verify(repository, Mockito.times(1)).findByIdAndStoreIdAndMarkForDelete(DEFAULT_ID,
        STORE_ID, false);
    Assert.assertTrue(result.equals(shouldBeReturned));
  }

  @Test
  public void testGetPositionDetailByIdAndStoreIdAndReturnException() throws Exception {
    Mockito.when(repository.findByIdAndStoreIdAndMarkForDelete(DEFAULT_ID, STORE_ID, false))
        .thenReturn(null);
    try {
      this.service.getPositionDetailByIdAndStoreId(DEFAULT_ID, STORE_ID);
    } catch (Exception e) {
      if (e instanceof ApplicationException) {
        Assert.assertEquals(
            "Can not find data :no such id = " + DEFAULT_ID + " and storeId = " + STORE_ID,
            e.getMessage());
      } else {
        Assert.assertTrue(false);
      }
    }
    Mockito.verify(repository, Mockito.times(1)).findByIdAndStoreIdAndMarkForDelete(DEFAULT_ID,
        STORE_ID, false);
  }

  @Test
  public void testInsertNewPosition() {
    Position testSavePosition = new Position();
    testSavePosition.setId(DEFAULT_ID);
    testSavePosition.setStoreId(STORE_ID);
    testSavePosition.setTitle("Choa");
    testSavePosition.setId("122");
    testSavePosition.setStoreId("Store");
    Mockito.when(repository.save(position)).thenReturn(testSavePosition);
    Position result = this.service.insertNewPosition(testSavePosition);
    Mockito.verify(repository, Mockito.times(2)).save(testSavePosition);
    Assert.assertTrue(result.equals(testSavePosition));
  }

  @Test
  public void testUpdatePositionInformation() throws Exception {
    this.service.setGdnMapper(gdnMapper);
    Position updatedPosition = new Position();
    updatedPosition.setId(position.getId());
    updatedPosition.setStoreId(position.getStoreId());
    updatedPosition.setTitle("New Title");
    updatedPosition.setJobDivision("Job Division");
    updatedPosition.setJobType("Job Type");
    Mockito.when(this.repository.findOne(updatedPosition.getId())).thenReturn(this.position);
    Mockito.when(this.repository.save(updatedPosition)).thenReturn(updatedPosition);
    this.service.updatePositionInformation(updatedPosition);
    verify(this.repository).findOne(updatedPosition.getId());
    verify(this.repository, Mockito.times(2)).save(updatedPosition);
  }

}
