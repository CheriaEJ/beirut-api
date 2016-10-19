package com.gdn.x.beirut.dto.response;

import java.util.Date;

import com.gdn.common.web.base.BaseResponse;

public class CandidatePositionSolrDTOResponse extends BaseResponse {

  /**
   *
   */
  private static final long serialVersionUID = 1814098341996849961L;

  private String idCandidate;

  private String idPosition;

  private String emailAddress;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private Date createdDate;

  private String title;

  private String status;

  private String jobType;

  private String jobDivision;

  @Override
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

  private String getJobDivision() {
    return jobDivision;
  }

  private String getJobType() {
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

  public String getTitle() {
    return title;
  }

  @Override
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

  private void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  private void setJobType(String jobType) {
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

  public void setTitle(String title) {
    this.title = title;
  }
}
