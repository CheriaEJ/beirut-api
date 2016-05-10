package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;


public interface CandidateDAO extends JpaRepository<Candidate, String> {
  // List<CandidatePosition> findAllCandidatePositions();

  List<Candidate> findByCreatedDateBetween(Date start, Date end);

  List<Candidate> findByEmailAddress(String emailaddress);

  List<Candidate> findByFirstName(String firstname);

  List<Candidate> findByFirstNameLike(String firstname);

  List<Candidate> findByLastName(String lastname);

  List<Candidate> findByLastNameLike(String lastname);

  List<Candidate> findByPhoneNumber(String phonenumber);

  List<Candidate> findByPhoneNumberLike(String phonenumber);
  // @Query("from CandidatePosition where id = :id")
  // CandidatePosition findCandidatePositionById(@[param("id") String id);
  //
  // }
}
