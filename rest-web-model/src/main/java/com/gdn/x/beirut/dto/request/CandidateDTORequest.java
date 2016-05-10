package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDTORequest extends BaseRequest {

  private CandidateDetailDTORequest candidateDetail;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;

  public CandidateDetailDTORequest getCandidateDetail() {
    return candidateDetail;
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

  public void setCandidateDetail(CandidateDetailDTORequest candidateDetail) {
    this.candidateDetail = candidateDetail;
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

}
