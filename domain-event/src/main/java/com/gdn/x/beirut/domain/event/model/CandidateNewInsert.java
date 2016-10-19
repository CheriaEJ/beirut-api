package com.gdn.x.beirut.domain.event.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateNewInsert extends GdnBaseDomainEventModel {

  private String storeId;

  private String idPosition;

  private String idCandidate;

  private String emailAddress;

  private String firstName;

  private String lastName;

  private String jobDivision;

  private String jobType;

  private String phoneNumber;

  private Date createdDate;

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

  public String getJobDivision() {
    return jobDivision;
  }

  public String getJobType() {
    return jobType;
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

  public void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
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
    return "CandidateNewInsert [storeId=" + storeId + ", idPosition=" + idPosition
        + ", idCandidate=" + idCandidate + ", emailAddress=" + emailAddress + ", firstName="
        + firstName + ", lastName=" + lastName + ", jobDivision=" + jobDivision + ", jobType="
        + jobType + ", phoneNumber=" + phoneNumber + ", createdDate=" + createdDate + ", title="
        + title + ", status=" + status + ", toString()=" + super.toString() + "]";
  }

}
