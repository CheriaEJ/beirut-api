package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDTOResponse extends BaseResponse {

  private static final long serialVersionUID = -359419677282632955L;
  private CandidateDetailDTOResponse candidateDetail;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private boolean markForDelete;

  public CandidateDetailDTOResponse getCandidateDetail() {
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

  public boolean isMarkForDelete() {
    return markForDelete;
  }

  public void setCandidateDetail(CandidateDetailDTOResponse candidateDetail) {
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

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
