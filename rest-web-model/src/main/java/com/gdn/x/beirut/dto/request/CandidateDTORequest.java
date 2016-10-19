package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDTORequest extends BaseRequest {

  private static final long serialVersionUID = 3040418122981154523L;
  private String id;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private List<String> positionIds;

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public List<String> getPositionIds() {
    return positionIds;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPositionIds(List<String> positionIds) {
    this.positionIds = positionIds;
  }


}
