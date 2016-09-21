package com.gdn.x.beirut.domain.event.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyNewPosition extends GdnBaseDomainEventModel {

  private String storeId;

  private String idPosition;

  private String idCandidate;

  private String emailAddress;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private Date createdDate;

  private String jobType;

  private String jobDivision;

  private String title;

  private String status;

  public Date getCreatedDate() {
    return createdDate;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getIdCandidate() {
    return idCandidate;
  }

  public String getIdPosition() {
    return idPosition;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getStatus() {
    return status;
  }

  public String getStoreId() {
    return storeId;
  }

  public String getTitle() {
    return title;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
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


  public void setIdPosition(String idPosition) {
    this.idPosition = idPosition;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "ApplyNewPosition [storeId=" + storeId + ", idPosition=" + idPosition + ", idCandidate="
        + idCandidate + ", emailAddress=" + emailAddress + ", firstName=" + firstName
        + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", createdDate=" + createdDate
        + ", title=" + title + ", status=" + status + ", toString()=" + super.toString() + "]";
  }


}
