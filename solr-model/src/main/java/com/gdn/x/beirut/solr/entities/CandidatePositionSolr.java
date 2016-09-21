package com.gdn.x.beirut.solr.entities;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.gdn.common.base.entity.GdnBaseSolrEntity;

@SolrDocument(solrCoreName = "xcandidatePosition")
public class CandidatePositionSolr extends GdnBaseSolrEntity {
  private static final long serialVersionUID = 1L;

  @Field
  private String idCandidate;

  @Field
  private String idPosition;

  @Field
  private String emailAddress;

  @Field
  private String firstName;

  @Field
  private String lastName;

  @Field
  private String phoneNumber;

  @Field
  private Date createdDate;

  @Field
  private String jobType;

  @Field
  private String jobDivision;

  @Field
  private String title;

  @Field
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

  @Override
  public String toString() {
    return String.format(
        "CandidateSolr [emailAddress=%s, firstName=%s, lastName=%s, phoneNumber=%s, status=%s, title=%s, toString()=%s]",
        emailAddress, firstName, lastName, phoneNumber, status, title, super.toString());
  }
}
