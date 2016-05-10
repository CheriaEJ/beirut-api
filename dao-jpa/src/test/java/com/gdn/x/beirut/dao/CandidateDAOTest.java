package com.gdn.x.beirut.dao;

import java.util.GregorianCalendar;
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

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class CandidateDAOTest {

  @Autowired
  private CandidateDAO candidateDAO;

  @Before
  public void initialize() {

    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate("1");
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      CandidateDetail candDetail = new CandidateDetail("1");
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      this.candidateDAO.save(newCandidate);
    }

    Candidate newCandidate = new Candidate("1");
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    CandidateDetail candDetail = new CandidateDetail("1");
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidateDetail(candDetail);
    this.candidateDAO.save(newCandidate);
  }

  @Test
  public void testFindByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    List<Candidate> res =
        this.candidateDAO.findByCreatedDateBetween(start.getTime(), end.getTime());
    for (Candidate candidate : res) {
      Assert.assertTrue(start.getTime().getTime() <= candidate.getCreatedDate().getTime()
          && end.getTime().getTime() >= candidate.getCreatedDate().getTime());
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByCreatedDateBetweenNoResult() {
    GregorianCalendar start = new GregorianCalendar(1900, 1, 1);
    GregorianCalendar end = new GregorianCalendar(1900, 6, 1);
    List<Candidate> res =
        this.candidateDAO.findByCreatedDateBetween(start.getTime(), end.getTime());
    res.get(0);
  }

  @Test
  public void testFindByEmailAddress() {
    List<Candidate> res = this.candidateDAO.findByEmailAddress("egaprianto1@asd.com");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertTrue(new String(res.get(0).getCandidateDetail().getContent()).equals("ini PDF1"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByEmailAddressOnlyOneResult() {
    List<Candidate> res = this.candidateDAO.findByEmailAddress("egaprianto1@asd.com");
    res.get(1);
  }

  @Test
  public void testFindByFirstName() {
    List<Candidate> res = this.candidateDAO.findByFirstName("Ega");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.get(0).getCandidateDetail().getContent())
        .equals(new String(res.get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.get(0).getFirstName().equals("Ega"));
    Assert.assertTrue(res.get(0).getFirstName().equals(res.get(1).getFirstName()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByFirstNameNoResult() {
    List<Candidate> res = this.candidateDAO.findByFirstName("asd");
    res.get(0);
  }

  @Test
  public void testFindByLastName() {
    List<Candidate> res = this.candidateDAO.findByLastName("Prianto");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.get(0).getCandidateDetail().getContent())
        .equals(new String(res.get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.get(0).getLastName().equals("Prianto"));
    Assert.assertTrue(res.get(0).getLastName().equals(res.get(1).getLastName()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByLastNameNoResult() {
    List<Candidate> res = this.candidateDAO.findByLastName("asd");
    res.get(0);
  }

  @Test
  public void testFindByPhoneNumber() {
    List<Candidate> res = this.candidateDAO.findByPhoneNumber("1234567890");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertTrue(res.get(0).getPhoneNumber().equals("1234567890"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByPhoneNumberSupposedOnlyTwoResult() {
    List<Candidate> res = this.candidateDAO.findByPhoneNumber("1234567890");
    res.get(2);
  }

}
