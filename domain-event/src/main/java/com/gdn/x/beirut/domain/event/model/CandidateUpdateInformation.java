package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateUpdateInformation extends GdnBaseDomainEventModel {
  private String storeId;

  private String idCandidate;

  private String emailAddress;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getIdCandidate() {
    return idCandidate;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setIdCandidate(String idCandidate) {
    this.idCandidate = idCandidate;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  @Override
  public String toString() {
    return "CandidateUpdateInformation [storeId=" + storeId + ", idCandidate=" + idCandidate
        + ", emailAddress=" + emailAddress + ", firstName=" + firstName + ", lastName=" + lastName
        + ", phoneNumber=" + phoneNumber + ", toString()=" + super.toString() + "]";
  }
}
