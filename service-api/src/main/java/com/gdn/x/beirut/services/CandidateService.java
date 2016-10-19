package com.gdn.x.beirut.services;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Status;

public interface CandidateService {

  public Candidate applyNewPosition(String candidateId, List<String> positionId) throws Exception;

  public Candidate createNew(Candidate candidate, List<String> positionIds) throws Exception;

  public List<Candidate> getAllCandidates();

  public Page<Candidate> getAllCandidatesByStoreIdAndMarkForDeletePageable(String storeId,
      boolean markForDelete, Pageable pageable) throws Exception;

  public Page<Candidate> getAllCandidatesByStoreIdPageable(String storeId, Pageable pageable)
      throws Exception;

  public Page<Candidate> getAllCandidatesWithPageable(String storeId, Pageable pageable);

  public Candidate getCandidate(String id) throws Exception;

  public Candidate getCandidateByIdAndStoreIdEager(String id, String storeId) throws Exception;

  public Candidate getCandidateByIdAndStoreIdLazy(String id, String storeId) throws Exception;

  public CandidateDetail getCandidateDetailAndStoreId(String id, String storeId) throws Exception;

  public CandidatePosition getCandidatePositionByStoreIdWithLogs(String idCandidate,
      String idPosition, String storeId) throws Exception;

  public void markForDelete(List<String> ids) throws Exception;

  public void markForDelete(String id) throws Exception;

  public Page<Candidate> searchByCreatedDateBetweenAndStoreId(Date start, Date end, String storeId,
      Pageable pageable);

  public Page<Candidate> searchByFirstNameContainAndStoreId(String firstname, String storeId,
      Pageable pageable) throws Exception;

  public Page<Candidate> searchByLastNameContainAndStoreId(String lastName, String storeId,
      Pageable pageable);

  public Candidate searchCandidateByEmailAddressAndStoreId(String emailAddress, String storeId);

  public List<Candidate> searchCandidateByPhoneNumber(String phoneNumber);

  public Page<Candidate> searchCandidateByPhoneNumberContainAndStoreId(String phoneNumber,
      String storeId, Pageable pageable);

  public void updateCandidateDetail(String storeId, Candidate candidate) throws Exception;

  public boolean updateCandidateInformation(Candidate newCandidateInformation)
      throws ApplicationException;

  public void updateCandidateStatus(String storeid, String candidateId, String idPosition,
      Status status) throws Exception;

  public void updateCandidateStatusBulk(String storeId, List<String> idCandidates,
      String idPosition, Status status) throws Exception;
}
