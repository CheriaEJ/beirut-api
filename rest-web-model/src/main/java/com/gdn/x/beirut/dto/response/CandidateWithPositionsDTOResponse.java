package com.gdn.x.beirut.dto.response;

import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class CandidateWithPositionsDTOResponse extends BaseResponse {
  private static final long serialVersionUID = -2381755997050065098L;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private boolean markForDelete;
  Set<CandidatePositionDTOResponse> candidatePositions;

  public Set<CandidatePositionDTOResponse> getCandidatePositions() {
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

  public boolean isMarkForDelete() {
    return markForDelete;
  }

  public void setCandidatePositions(Set<CandidatePositionDTOResponse> candidatePositions) {
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

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String toStringz() {
    String res = "CandidateWithPositionsDTOResponse [emailAddress=" + emailAddress + ", firstName="
        + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", markForDelete="
        + markForDelete + ", candidatePositions=[";
    if (candidatePositions != null && candidatePositions.size() > 0) {
      for (CandidatePositionDTOResponse candidatePositionDTOResponse : candidatePositions) {
        res += candidatePositionDTOResponse.toStringz() + ",";
      }
    }
    String end = "]]";
    return res + end;
  }
}
