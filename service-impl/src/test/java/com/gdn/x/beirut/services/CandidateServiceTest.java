package com.gdn.x.beirut.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gdn.common.base.domainevent.publisher.DomainEventPublisher;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.domain.event.model.CandidateNewInsert;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.entities.StatusLog;

public class CandidateServiceTest {

  private static final String ID = "ID";

  private static final String STORE_ID = "MYID";

  private static final String FIRST_NAME = "RIZAL";

  private static final String LIKE_FIRST_NAME = "IZA";

  private static final String LIKE_LAST_NAME = "AHRI";

  private static final String LAST_NAME = "FAHRI";

  private static final Status STATUS = Status.APPLY;

  private static final Pageable DEFAULT_PAGEABLE = PageableHelper.generatePageable(0, 10);

  private static final String POSITION_ID = "POSITION_ID";


  @Mock
  private CandidateDAO candidateDao;

  @Mock
  private PositionDAO positionDao;

  @Mock
  private DomainEventPublisher domainEventPublisher;

  @InjectMocks
  private CandidateServiceImpl candidateService;

  private GdnMapper gdnMapper;

  private Candidate candidate;

  private Candidate markForDeleteCandidate;

  private CandidateDetail candidateDetail;

  private Candidate candidateWithDetail;

  private List<Candidate> candidates;

  private Page<Candidate> candidatePage;

  private List<Candidate> candidateRanges;

  private Page<Candidate> candidateRangePage;

  private Position position;

  @Before
  public void initialize() {
    initMocks(this);
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

    this.candidateService.setGdnMapper(this.gdnMapper);

    this.position = new Position();
    this.position.setId(ID);
    this.position.setStoreId(STORE_ID);
    this.position.setTitle("Position Title");

    this.candidate = new Candidate();
    this.candidate.setId(ID);
    this.candidate.setStoreId(STORE_ID);
    this.candidate.setFirstName(FIRST_NAME);
    this.candidate.setLastName(LAST_NAME);

    this.markForDeleteCandidate = new Candidate();
    this.markForDeleteCandidate.setId(ID);
    this.markForDeleteCandidate.setStoreId(STORE_ID);
    this.markForDeleteCandidate.setFirstName(FIRST_NAME);
    this.markForDeleteCandidate.setLastName(LAST_NAME);
    this.markForDeleteCandidate.setMarkForDelete(true);

    this.candidateDetail = new CandidateDetail();
    this.candidateDetail.setId(ID);

    this.candidateWithDetail = new Candidate();
    this.candidateWithDetail.setId(ID);
    this.candidateWithDetail.setStoreId(STORE_ID);
    this.candidateWithDetail.setFirstName(FIRST_NAME);
    this.candidateWithDetail.setLastName(LAST_NAME);
    this.candidateWithDetail.setCandidateDetail(this.candidateDetail);

    this.candidates = new ArrayList<Candidate>();
    this.candidates.add(this.candidate);

    candidatePage = new PageImpl<Candidate>(candidates);

    when(this.candidateDao.findByFirstNameContainingAndStoreId(LIKE_FIRST_NAME, STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(this.candidatePage);
    when(this.candidateDao.findByLastNameContainingAndStoreId(LIKE_LAST_NAME, STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(this.candidatePage);
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    when(this.candidateDao.save(this.candidate)).thenReturn(this.candidate);
    candidateRanges = new ArrayList<>();
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar create = new GregorianCalendar(2016, 2, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate();
      newCandidate.setId(ID + " " + i);
      newCandidate.setStoreId(STORE_ID);
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      newCandidate.setCreatedDate(create.getTime());
      CandidateDetail candDetail = new CandidateDetail();
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      candidates.add(newCandidate);
    }

    Candidate newCandidate = new Candidate();
    newCandidate.setId(ID);
    newCandidate.setStoreId(STORE_ID);
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    newCandidate.setCreatedDate(create.getTime());
    CandidateDetail candDetail = new CandidateDetail();
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidateDetail(candDetail);
    candidateRanges.add(newCandidate);

    candidateRangePage = new PageImpl<Candidate>(candidateRanges);

    for (int i = 0; i < 10; i++) {
      Candidate toBeDeletedCandidate = new Candidate();
      toBeDeletedCandidate.setId(ID + " " + i);
      toBeDeletedCandidate.setStoreId(STORE_ID);
      toBeDeletedCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      toBeDeletedCandidate.setFirstName("Ega");
      toBeDeletedCandidate.setLastName("Prianto");
      toBeDeletedCandidate.setPhoneNumber("123456789" + i);
      toBeDeletedCandidate.setCreatedDate(create.getTime());
      toBeDeletedCandidate.setMarkForDelete(false);
      CandidateDetail candidateDetail = new CandidateDetail();
      candidateDetail.setContent(("ini PDF" + i).getBytes());
      toBeDeletedCandidate.setCandidateDetail(candidateDetail);
      candidates.add(toBeDeletedCandidate);
      Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID + " " + i, false))
          .thenReturn(toBeDeletedCandidate);
    }

    when(this.candidateDao.findByCreatedDateBetweenAndStoreId(start.getTime(), end.getTime(),
        STORE_ID, DEFAULT_PAGEABLE)).thenReturn(candidateRangePage);
  }

  @After
  public void noMoreTransaction() {
    Mockito.verifyNoMoreInteractions(this.candidateDao);
    Mockito.verifyNoMoreInteractions(this.positionDao);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testApplyNewPosition() throws Exception {
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      positionIds.add(UUID.randomUUID().toString());
    }
    List<Position> positions = new ArrayList<>();
    int i = 1;
    for (String string : positionIds) {
      Position newPosition = new Position();
      newPosition.setId(string);
      newPosition.setTitle("Title" +i);
      positions.add(newPosition);
      i++;
    }
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    Mockito.when(this.positionDao.findAll()).thenReturn(positions);
    for (Position position : positions) {
      CandidatePosition candidatePosition =
          new CandidatePosition(candidate, position, candidate.getStoreId());
      StatusLog statusLog = new StatusLog();
      statusLog.setStoreId(candidate.getStoreId());
      statusLog.setCandidatePosition(candidatePosition);
      statusLog.setStatus(Status.APPLY);
      candidate.getCandidatePositions().add(candidatePosition);
    }
    Mockito.when(this.candidateDao.save(candidate)).thenReturn(candidate);
    Candidate result = this.candidateService.applyNewPosition(ID, positionIds);
    //White box
    Mockito.verify(this.candidateDao,times(1)).findOne(ID);
    Mockito.verify(this.positionDao,times(1)).findAll();
    Mockito.verify(this.candidateDao,times(1)).save(candidate);
    //Black box
    Assert.assertTrue(result.getId().equals(ID));
    Assert.assertTrue(result.getFirstName().equals(FIRST_NAME));
    Assert.assertTrue(result.getLastName().equals(LAST_NAME));
    /*
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName(FIRST_NAME);
    candidate.setLastName(LAST_NAME);
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    Mockito.when(this.positionDao.findAll(positionIds)).thenReturn(positions);
    for (Position position : positions) {
      candidate.getCandidatePositions().add(new CandidatePosition(candidate, position, STORE_ID));
    }

    Assert.assertTrue(
        this.candidateService.applyNewPosition(ID, positionIds).getId() == candidate.getId());

    List<CandidatePosition> candPostTest =
        this.candidateService.applyNewPosition(ID, positionIds).getCandidatePositions();
    int i = 0;
    for (CandidatePosition candidatePosition : candPostTest) {
      Assert.assertTrue(candidatePosition.getPosition().getId() == POSITION_ID + i);
      Assert.assertTrue(candidatePosition.getPosition().getStoreId() == STORE_ID);
      Assert.assertTrue(candidatePosition.getPosition().getTitle() == "Title : " + i);
      i++;
    }
    this.candidateService.applyNewPosition(ID, positionIds);
    verify(this.candidateDao, times(3)).findOne(ID);
    verify(this.positionDao, times(3)).findAll(positionIds);
    verify(this.candidateDao, times(3)).save(candidate);
    // verify(this.candidateDao, times(3)).find
     *
     */
    }

  @Test(expected = Exception.class)
  public void testApplyNewPositionException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName(FIRST_NAME);
    candidate.setLastName(LAST_NAME);
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(null);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    try {
      this.candidateService.applyNewPosition(ID, positionIds);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
    // verify(this.positionDao, times(1)).findAll(positionIds);
    // verify(this.candidateDao, times(1)).save(candidate);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGetAllCandidates() {
    Candidate cand1 = new Candidate();
    cand1.setId(ID);
    cand1.setStoreId(STORE_ID);
    Candidate cand2 = new Candidate();
    cand2.setId(ID);
    cand2.setStoreId(STORE_ID);
    List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(cand1);
    candidates.add(cand2);
    when(this.candidateDao.findAll()).thenReturn(candidates);

    // Black Box Test
    Assert.assertTrue(this.candidateService.getAllCandidates() == candidates);
    // White Box Test
    this.candidateService.getAllCandidates();
    verify(this.candidateDao, times(2)).findAll();
  }

  @Test
  public void testGetAllCandidatesByStoreIdPageable() throws Exception {
    List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(this.candidate);
    Page<Candidate> pageCandidates =
        new PageImpl<>(candidates, DEFAULT_PAGEABLE, candidates.size());
    Mockito.when(this.candidateDao.findByStoreId(STORE_ID, DEFAULT_PAGEABLE))
        .thenReturn(pageCandidates);
    assertTrue(this.candidateService.getAllCandidatesByStoreIdPageable(STORE_ID,
        DEFAULT_PAGEABLE) == pageCandidates);
    this.candidateService.getAllCandidatesByStoreIdPageable(STORE_ID, DEFAULT_PAGEABLE);
    verify(this.candidateDao, times(2)).findByStoreId(STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testGetCandidate() throws Exception {
    this.candidate.setEmailAddress("email address");
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    Candidate result = this.candidateService.getCandidate(ID);
    result.setEmailAddress("email address");
    // Black Box Test
    assertTrue(result.getId().equals(this.candidate.getId()));
    assertTrue(result.getStoreId().equals(this.candidate.getStoreId()));
    assertTrue(result.getFirstName().equals(this.candidate.getFirstName()));
    assertTrue(result.getLastName().equals(this.candidate.getLastName()));
    assertTrue(result.getEmailAddress().equals(this.candidate.getEmailAddress()));
    // White Box Test
    Mockito.verify(this.candidateDao, Mockito.times(1)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidateAndReturnException() throws Exception {
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(null);
    try {
      this.candidateService.getCandidate(ID);
    } catch (Exception e) {
      Mockito.verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test
  public void testGetCandidateByIdAndStoreIdEager() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    assertTrue(
        this.candidateService.getCandidateByIdAndStoreIdEager(ID, STORE_ID) == this.candidate);
    this.candidateService.getCandidateByIdAndStoreIdEager(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidateByIdAndStoreIdEagerException() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(null);
    try {
      this.candidateService.getCandidateByIdAndStoreIdEager(ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test
  public void testGetCandidateByIdAndStoreIdLazy() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    assertTrue(
        this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID) == this.candidate);
    this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidateByIdAndStoreIdLazy_IdException() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(null);
    try {
      this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void testGetCandidateByIdAndStoreIdLazy_StoreIdException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID + "false");
    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    try {
      this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }


  @Test
  public void testGetCandidateDetailAndStoreId() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidateWithDetail);
    // Black Box Test
    assertTrue(
        this.candidateService.getCandidateDetailAndStoreId(ID, STORE_ID) == this.candidateDetail);
    // White Box Test
    this.candidateService.getCandidateDetailAndStoreId(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidateDetailAndStoreId_IdException() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(null);
    // Black Box Test
    try {
      this.candidateService.getCandidateDetailAndStoreId(ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void testGetCandidateDetailAndStoreId_StoreIdException() throws Exception {
    Candidate candidateWithDetail = new Candidate();
    candidateWithDetail.setId(ID);
    candidateWithDetail.setStoreId(STORE_ID + "fail");
    candidateWithDetail.setFirstName(FIRST_NAME);
    candidateWithDetail.setLastName(LAST_NAME);
    candidateWithDetail.setCandidateDetail(this.candidateDetail);
    when(this.candidateDao.findOne(ID)).thenReturn(candidateWithDetail);
    // Black Box Test
    try {
      this.candidateService.getCandidateDetailAndStoreId(ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test
  public void testGetCandidatePositionWithLogs() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID);
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    List<CandidatePosition> sets = new ArrayList<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);

    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    when(this.positionDao.findOne(ID)).thenReturn(position);

    assertTrue(
        this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID) == candPost);
    this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID);

    verify(this.positionDao, times(2)).findOne(ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidatePositionWithLogs_IdCandidateException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID);
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    List<CandidatePosition> sets = new ArrayList<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);

    when(this.candidateDao.findOne(ID)).thenReturn(null);
    when(this.positionDao.findOne(ID)).thenReturn(position);

    try {
      this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void testGetCandidatePositionWithLogs_IdPositionException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID);
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    List<CandidatePosition> sets = new ArrayList<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);

    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    when(this.positionDao.findOne(ID)).thenReturn(null);

    try {
      this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      verify(this.positionDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void testGetCandidatePositionWithLogs_StoreIdCandidateException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID + "fail");
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID);
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    List<CandidatePosition> sets = new ArrayList<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);

    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    when(this.positionDao.findOne(ID)).thenReturn(position);

    try {
      this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void testGetCandidatePositionWithLogs_StoreIdPositionException() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID + "fail");
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    List<CandidatePosition> sets = new ArrayList<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);

    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    when(this.positionDao.findOne(ID)).thenReturn(position);

    try {
      this.candidateService.getCandidatePositionByStoreIdWithLogs(ID, ID, STORE_ID);
    } catch (Exception e) {
      verify(this.candidateDao, times(1)).findOne(ID);
      verify(this.positionDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test
  public void testMarkForDelete() throws Exception {
    Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID, false)).thenReturn(candidate);
    this.candidateService.markForDelete(ID);
    verify(this.candidateDao, times(1)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testMarkForDeleteBulk() throws Exception {
    List<String> ids = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String id = ID + " " + i;
      ids.add(id);
    }
    this.candidateService.markForDelete(ids);
    // Black Box Test
    // White Box Test
    verify(this.candidateDao, times(10)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    final Candidate candidate = this.candidate;
    candidate.setMarkForDelete(true);
    verify(this.candidateDao, times(10)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testSave() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
      CandidatePosition candidatePosition = new CandidatePosition(candidate, position, STORE_ID);
      position.setCandidatePositions(new ArrayList<CandidatePosition>());
      position.getCandidatePositions().add(candidatePosition);
      candidate.getCandidatePositions().add(candidatePosition);
    }
    Mockito.when(this.candidateDao.save(candidate)).thenReturn(candidate);
    Mockito.when(this.positionDao.findAll(positionIds)).thenReturn(positions);
    // Black Box Test
    // White Box Test

    this.candidateService.createNew(this.candidate, positionIds);
    verify(this.candidateDao, times(1)).save(this.candidate);
    verify(this.positionDao, times(1)).findAll(positionIds);
    verify(this.domainEventPublisher, times(10)).publish(Mockito.any(CandidateNewInsert.class),
        Mockito.matches(DomainEventName.CANDIDATE_NEW_INSERT), Mockito.any());
  }

  @Test
  public void testSaveAndReturnException() throws Exception {
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    Mockito.when(this.candidateDao.save(Mockito.any(Candidate.class)))
        .thenThrow(new RuntimeException());
    try {
      this.candidateService.createNew(candidate, positionIds);
    } catch (Exception e) {
      verify(this.positionDao, times(1)).findAll(positionIds);
    }
  }

  @Test
  public void testSearchByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    long end = System.currentTimeMillis();
    List<Candidate> candidates = new ArrayList<>();
    for (Candidate candidate : this.candidates) {
      if (candidate.getCreatedDate() != null && candidate.getCreatedDate().getTime() < end
          && candidate.getCreatedDate().getTime() > start.getTime().getTime()) {
        candidates.add(candidate);
      }
    }
    Page<Candidate> pageCandidates =
        new PageImpl<>(candidates, DEFAULT_PAGEABLE, candidates.size());
    Mockito.when(this.candidateDao.findByCreatedDateBetweenAndStoreId(start.getTime(),
        new Date(end), STORE_ID, DEFAULT_PAGEABLE)).thenReturn(pageCandidates);
    List<Candidate> result =
        this.candidateService.searchByCreatedDateBetweenAndStoreId(start.getTime(), new Date(end),
            STORE_ID, DEFAULT_PAGEABLE).getContent();
    // Black Box Test
    Assert.assertTrue(result.size() == 20);
    for (Candidate candidate : result) {
      Assert.assertTrue(candidate.getCreatedDate().getTime() > start.getTime().getTime()
          && candidate.getCreatedDate().getTime() < end);
      Assert.assertTrue(candidate.getStoreId().equals(STORE_ID));
    }
    // White Box Test
    verify(this.candidateDao, times(1)).findByCreatedDateBetweenAndStoreId(start.getTime(),
        new Date(end), STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testSearchByFirstName() throws Exception {
    Page<Candidate> result = this.candidateService
        .searchByFirstNameContainAndStoreId(LIKE_FIRST_NAME, STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    for (Candidate candidate : result) {
      Assert.assertTrue(candidate.getFirstName().contains(LIKE_FIRST_NAME));
    }
    // White Box Test
    verify(this.candidateDao, times(1)).findByFirstNameContainingAndStoreId(LIKE_FIRST_NAME,
        STORE_ID, DEFAULT_PAGEABLE);

  }

  @Test
  public void testSearchByLastName() {
    Page<Candidate> searchByLastNameContainAndStoreId = this.candidateService
        .searchByLastNameContainAndStoreId(LIKE_LAST_NAME, STORE_ID, DEFAULT_PAGEABLE);
    // White Box Test (CEK PEMANGGILAN)
    verify(this.candidateDao, times(1)).findByLastNameContainingAndStoreId(LIKE_LAST_NAME, STORE_ID,
        DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(searchByLastNameContainAndStoreId.getContent().size() == 1);
  }

  @Test
  public void testSearchByPhoneNumberContainingAndStoreId() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidates) {
      if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().contains("123456789")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(candidatePage);
    Page<Candidate> result = this.candidateService
        .searchCandidateByPhoneNumberContainAndStoreId("123456789", STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(result.getContent().size() == 1);
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE);
  }

  // @Test
  // public void testSearchCandidateByPhoneNumber() {
  // List<Candidate> res = new ArrayList<>();
  // for (Candidate candidate : candidates) {
  // if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().equals("1234567890")) {
  // res.add(candidate);
  // }
  // }
  // Page<Candidate> pageRes = new PageImpl<>(res, DEFAULT_PAGEABLE, res.size());
  // when(this.candidateDao.findByPhoneNumberContainingAndStoreId("1234567890", STORE_ID,
  // DEFAULT_PAGEABLE)).thenReturn(pageRes);
  // List<Candidate> result = this.candidateService.searchCandidateByPhoneNumber("1234567890");
  // // Black Box Test
  // Assert.assertTrue(result.equals(res));
  // // White Box Test
  // verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("1234567890",
  // STORE_ID, DEFAULT_PAGEABLE);
  // }

  @Test
  public void testSearchCandidateByEmailAddress() {
    // List<Candidate> res = new ArrayList<>();
    // for (Candidate candidate : candidateRanges) {
    // if (candidate.getEmailAddress().equals("egaprianto@asd.com")) {
    // res.add(candidate);
    // }
    // }
    candidate.setEmailAddress("egaprianto@asd.com");
    when(this.candidateDao.findByEmailAddressAndStoreId("egaprianto@asd.com", STORE_ID))
        .thenReturn(candidate);
    Candidate result = this.candidateService
        .searchCandidateByEmailAddressAndStoreId("egaprianto@asd.com", STORE_ID);
    // Black Box Test
    Assert.assertTrue(result.equals(candidate));
    // White Box Test
    verify(this.candidateDao, times(1)).findByEmailAddressAndStoreId("egaprianto@asd.com",
        STORE_ID);
  }


  // @Test
  public void testSearchCandidateByPhoneNumber() {
    List<Candidate> expected = new ArrayList<>();
    for (Candidate candidate : candidates) {
      if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().equals("1234567890")) {
        expected.add(candidate);
      }
    }
    Page<Candidate> pageExpected = new PageImpl<>(expected, DEFAULT_PAGEABLE, expected.size());
    when(this.candidateDao.findByPhoneNumberContainingAndStoreId("1234567890", STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(pageExpected);
    Page<Candidate> result = this.candidateService
        .searchCandidateByPhoneNumberContainAndStoreId("1234567890", STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    for (Candidate candidate : result.getContent()) {
      assertTrue(candidate.getPhoneNumber().contains("1234567890"));
    }
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("1234567890",
        STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testSearchCandidateByPhoneNumberLike() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidates) {
      if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().contains("123456789")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(candidatePage);
    Page<Candidate> result = this.candidateService
        .searchCandidateByPhoneNumberContainAndStoreId("123456789", STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(result.getContent().size() == 1);
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE);
  }

  // getCandidatePositionWithLogs(String idCandidate, String idPosition)
  @Test
  public void testUpdateCandidateDetail() throws Exception {
    String detilTest = "contoh konten!!!";
    String firstTest = "Eve";
    String lastTest = "Adam";
    String phoneTest = "29382932";
    String emailTest = "email";
    candidateWithDetail.setPhoneNumber(phoneTest);
    candidateWithDetail.setEmailAddress(emailTest);
    candidateWithDetail.setFirstName(firstTest);
    candidateWithDetail.setLastName(lastTest);
    candidateWithDetail.getCandidateDetail().setContent((detilTest).getBytes());
    when(this.candidateDao.findOne(ID)).thenReturn(candidateWithDetail);
    Candidate updatedCandidate = candidateWithDetail;
    updatedCandidate.setPhoneNumber(phoneTest);
    updatedCandidate.setEmailAddress(emailTest);
    updatedCandidate.setFirstName(firstTest);
    updatedCandidate.setLastName(lastTest);
    updatedCandidate.getCandidateDetail().setContent((emailTest).getBytes());
    when(this.candidateDao.save(updatedCandidate)).thenReturn(updatedCandidate);

    this.candidateService.updateCandidateDetail(STORE_ID, updatedCandidate);

    // assertTrue(updatedCandidate.getFirstName().equals(candidateWithDetail.getFirstName()));
    // assertTrue(updatedCandidate.getLastName().equals(candidateWithDetail.getLastName()));
    // assertTrue(updatedCandidate.getEmailAddress().equals(candidateWithDetail.getEmailAddress()));
    // assertTrue(updatedCandidate.getPhoneNumber().equals(candidateWithDetail.getPhoneNumber()));
    // assertEquals(updatedCandidate.getCandidateDetail().getContent(),
    // candidateWithDetail.getCandidateDetail().getContent());

    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testUpdateCandidateInformation() throws Exception {
    Candidate newCandidateInformation = new Candidate();
    String UPDATED = "Updated";
    newCandidateInformation.setId(ID);
    newCandidateInformation.setFirstName(FIRST_NAME + UPDATED);
    newCandidateInformation.setLastName(LAST_NAME + UPDATED);
    newCandidateInformation.setStoreId(STORE_ID);
    when(this.candidateDao.findOne(newCandidateInformation.getId())).thenReturn(this.candidate);
    boolean result = this.candidateService.updateCandidateInformation(newCandidateInformation);
    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
    Assert.assertTrue(result);
  }

  @Test
  public void testUpdateCandidateStatus() throws Exception {
    when(this.positionDao.findOne(ID)).thenReturn(this.position);
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    Candidate testCandidate = candidate;
    List<CandidatePosition> candidatePositions = new ArrayList<CandidatePosition>();
    candidatePositions.add(new CandidatePosition(testCandidate, this.position, STORE_ID));
    testCandidate.setCandidatePositions(candidatePositions);
    when(this.candidateDao.findOne(ID)).thenReturn(testCandidate);
    this.candidateService.updateCandidateStatus(STORE_ID, testCandidate.getId(), ID, STATUS);
    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.positionDao, times(1)).findOne(ID);
    /*
     * Hibernate.initialize(testCandidate.getCandidatePositions());
     * testCandidate.getCandidatePositions().stream() .filter(candidatePosition ->
     * candidatePosition.getPosition().equals(position)) .forEach(candidatePosition -> {
     * candidatePosition.getStatusLogs().add(new StatusLog(candidatePosition, STATUS));
     * candidatePosition.setStatus(STATUS); // add missing setter zal });
     */
    verify(this.candidateDao, times(1)).save(testCandidate);
  }
}
