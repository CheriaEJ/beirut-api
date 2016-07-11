package com.gdn.x.beirut.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = Candidate.TABLE_NAME, uniqueConstraints = {
    @UniqueConstraint(columnNames = {Candidate.COLUMN_EMAIL_ADDRESS, Candidate.STORE_ID})})
public class Candidate extends GdnBaseEntity {

  private static final long serialVersionUID = -2726880350978769752L;
  public static final String TABLE_NAME = "candidate";
  public static final String COLUMN_EMAIL_ADDRESS = "email_address";
  public static final String COLUMN_FIRST_NAME = "first_name";
  public static final String COLUMN_LAST_NAME = "last_name";
  public static final String COLUMN_PHONE_NUMBER = "phone_number";

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private CandidateDetail candidateDetail;

  @Column(name = Candidate.COLUMN_EMAIL_ADDRESS)
  private String emailAddress;

  @Column(name = Candidate.COLUMN_FIRST_NAME)
  private String firstName;

  @Column(name = Candidate.COLUMN_LAST_NAME)
  private String lastName;

  @Column(name = Candidate.COLUMN_PHONE_NUMBER)
  private String phoneNumber;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private List<CandidatePosition> candidatePositions = new ArrayList<CandidatePosition>();

  public Candidate() {
    // nothing to do here
    // setId(UUID.randomUUID().toString());
  }

  public CandidateDetail getCandidateDetail() {
    return candidateDetail;
  }

  public List<CandidatePosition> getCandidatePositions() {
    return candidatePositions;
  }

  public String getEmailAddress() {
    return emailAddress;
  }


  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setCandidateDetail(CandidateDetail candidateDetail) {
    this.candidateDetail = candidateDetail;
  }

  public void setCandidatePositions(List<CandidatePosition> candidatePositions) {
    this.candidatePositions = candidatePositions;
  }


  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  // public String toStringz() {
  // String res = "Candidate [candidateDetail=" + candidateDetail + ", emailAddress=" + emailAddress
  // + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
  // + ", candidatePositions=[";
  // if (this.candidatePositions != null && this.candidatePositions.size() > 0) {
  // for (CandidatePosition candidatePosition : candidatePositions) {
  // res += candidatePosition.toStringz();
  // }
  // }
  // String end = "], getId()=" + getId() + ", getStoreId()=" + getStoreId() + "]";
  // return res + end;
  // }


}
