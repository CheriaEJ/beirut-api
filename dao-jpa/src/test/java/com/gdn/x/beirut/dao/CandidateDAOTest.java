package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class CandidateDAOTest {

  private static final String ID = "id_ini";

  private static final String STORE_ID = "store_id";

  private static final Pageable DEFAULT_PAGEABLE = PageableHelper.generatePageable(0, 100);

  @Autowired
  private CandidateDAO candidateDAO;

  @Before
  public void initialize() {
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate();
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      newCandidate.setStoreId(STORE_ID);
      CandidateDetail candDetail = new CandidateDetail();
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      candDetail.setCandidate(newCandidate);
      this.candidateDAO.save(newCandidate);
    }

    Candidate newCandidate = new Candidate();
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    CandidateDetail candDetail = new CandidateDetail();
    candDetail.setContent(("ini PDF").getBytes());
    candDetail.setCandidate(newCandidate);

    newCandidate.setCandidateDetail(candDetail);
    this.candidateDAO.save(newCandidate);
  }



  @Test
  public void testFindByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    Date end = new Date(System.currentTimeMillis());
    Page<Candidate> res = this.candidateDAO.findByCreatedDateBetweenAndStoreId(start.getTime(),
        new Date(end.getTime()), STORE_ID, DEFAULT_PAGEABLE);
    for (Candidate candidate : res) {
      Assert.assertTrue(start.getTime().getTime() <= candidate.getCreatedDate().getTime()
          && end.getTime() >= candidate.getCreatedDate().getTime());
      System.out.println(candidate.getCreatedDate().getTime() + "Time");
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByCreatedDateBetweenNoResult() {
    GregorianCalendar start = new GregorianCalendar(1900, 1, 1);
    GregorianCalendar end = new GregorianCalendar(1900, 6, 1);
    Page<Candidate> res = this.candidateDAO.findByCreatedDateBetweenAndStoreId(start.getTime(),
        end.getTime(), STORE_ID, DEFAULT_PAGEABLE);
    res.getContent().get(0);
  }

  @Test
  public void testFindByEmailAddressAndStoreId() {
    Candidate res = this.candidateDAO.findByEmailAddressAndStoreId("egaprianto1@asd.com", STORE_ID);
    Assert.assertNotNull(res.getFirstName());
    Assert.assertNotNull(res.getEmailAddress());
    Assert.assertNotNull(res.getLastName());
    Assert.assertNotNull(res.getPhoneNumber());
    Assert.assertNotNull(res.getCandidateDetail());
    Assert.assertTrue(new String(res.getCandidateDetail().getContent()).equals("ini PDF1"));
  }

  @Test
  public void testFindByFirstNameContaingAndStoreId() {
    Page<Candidate> res =
        this.candidateDAO.findByFirstNameContainingAndStoreId("Ega", STORE_ID, DEFAULT_PAGEABLE);
    Assert.assertNotNull(res.getContent().get(0).getFirstName());
    Assert.assertNotNull(res.getContent().get(0).getEmailAddress());
    Assert.assertNotNull(res.getContent().get(0).getLastName());
    Assert.assertNotNull(res.getContent().get(0).getPhoneNumber());
    Assert.assertNotNull(res.getContent().get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.getContent().get(0).getCandidateDetail().getContent())
        .equals(new String(res.getContent().get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.getContent().get(0).getFirstName().equals("Ega"));
    Assert.assertTrue(
        res.getContent().get(0).getFirstName().equals(res.getContent().get(1).getFirstName()));
  }

  @Test
  public void testFindByLastNameContainingAndStoreId() {
    Page<Candidate> res =
        this.candidateDAO.findByLastNameContainingAndStoreId("Prianto", STORE_ID, DEFAULT_PAGEABLE);
    Assert.assertNotNull(res.getContent().get(0).getFirstName());
    Assert.assertNotNull(res.getContent().get(0).getEmailAddress());
    Assert.assertNotNull(res.getContent().get(0).getLastName());
    Assert.assertNotNull(res.getContent().get(0).getPhoneNumber());
    Assert.assertNotNull(res.getContent().get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.getContent().get(0).getCandidateDetail().getContent())
        .equals(new String(res.getContent().get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.getContent().get(0).getLastName().equals("Prianto"));
    Assert.assertTrue(
        res.getContent().get(0).getLastName().equals(res.getContent().get(1).getLastName()));
  }

  // @Test(expected = IndexOutOfBoundsException.class)
  // public void testFindByLastNameNoResult() {
  // List<Candidate> res = this.candidateDAO.findByLastName("asd");
  // res.get(0);
  // }

  @Test
  public void testFindByPhoneNumberContainingAndStoreId() {
    Page<Candidate> res = this.candidateDAO.findByPhoneNumberContainingAndStoreId("1234567890",
        STORE_ID, DEFAULT_PAGEABLE);
    Assert.assertNotNull(res.getContent().get(0).getFirstName());
    Assert.assertNotNull(res.getContent().get(0).getEmailAddress());
    Assert.assertNotNull(res.getContent().get(0).getLastName());
    Assert.assertNotNull(res.getContent().get(0).getPhoneNumber());
    Assert.assertNotNull(res.getContent().get(0).getCandidateDetail());
    Assert.assertTrue(res.getContent().get(0).getPhoneNumber().equals("1234567890"));
  }


  @Test
  public void testFindByStoreId() {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID + "haha");
    candidate.setMarkForDelete(false);
    candidate.setFirstName("testFindByIdAndStoreId Firstname");
    candidate.setLastName("testFindByIdAndStoreId Lastname");
    this.candidateDAO.save(candidate);
    Assert.assertTrue(this.candidateDAO.findByStoreId(STORE_ID + "haha", DEFAULT_PAGEABLE)
        .getContent().get(0).getFirstName().equals("testFindByIdAndStoreId Firstname"));
  }

  @Test
  public void testFindByStoreIdAndMarkForDelete() {
    Candidate deletedCandidate = new Candidate();
    // deletedCandidate.setId(ID);
    deletedCandidate.setStoreId(STORE_ID + "12");
    deletedCandidate.setMarkForDelete(true);
    deletedCandidate.setFirstName("testFindByIdAndStoreId Firstname");
    deletedCandidate.setLastName("testFindByIdAndStoreId Lastname");
    this.candidateDAO.save(deletedCandidate);
    Assert.assertTrue(
        this.candidateDAO.findByStoreIdAndMarkForDelete(STORE_ID + "12", true, DEFAULT_PAGEABLE)
            .getContent().get(0).getFirstName().equals("testFindByIdAndStoreId Firstname"));
  }

  @Test
  public void testfindByStoreIdWithPageable() {
    // System.out.println(this.candidateDAO
    // .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 2000)).getContent().size());
    // System.out.println(this.candidateDAO
    // .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 4)).getContent().size());
    Assert.assertTrue(this.candidateDAO
        .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 4)).getContent().size() == 4);
    Assert.assertTrue(this.candidateDAO
        .findByStoreId(STORE_ID, PageableHelper.generatePageable(2, 4)).getContent().size() == 2);
  }

}
