package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;

public interface CandidateDAO extends JpaRepository<Candidate, String> {

  Page<Candidate> findByCreatedDateBetweenAndStoreId(Date start, Date end, String storeId,
      Pageable pageable);

  Candidate findByEmailAddressAndStoreId(String emailAddress, String storeId);

  @Deprecated
  List<Candidate> findByFirstName(String firstName);

  Page<Candidate> findByFirstNameContainingAndStoreId(String firstName, String storeId,
      Pageable pageable);

  Candidate findByIdAndMarkForDelete(String id, boolean markForDelete);

  @Deprecated
  List<Candidate> findByLastName(String lastName);

  Page<Candidate> findByLastNameContainingAndStoreId(String lastName, String storeId,
      Pageable pageable);

  @Deprecated
  List<Candidate> findByPhoneNumber(String phoneNumber);

  Page<Candidate> findByPhoneNumberContainingAndStoreId(String phoneNumber, String storeId,
      Pageable pageable);

  @Deprecated
  List<Candidate> findByStoreId(String storeId);

  Page<Candidate> findByStoreId(String storeId, Pageable pageable);

  Page<Candidate> findByStoreIdAndMarkForDelete(String storeId, boolean markForDelete,
      Pageable pageable);

}
