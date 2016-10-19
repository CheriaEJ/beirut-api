package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionUpdateInformation extends GdnBaseDomainEventModel {
  private String storeId;

  private String id;

  private String title;

  private String jobType;

  private String jobDivision;

  public String getId() {
    return id;
  }

  public String getJobDivision() {
    return jobDivision;
  }

  public String getJobType() {
    return jobType;
  }

  public String getStoreId() {
    return storeId;
  }

  public String getTitle() {
    return title;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "PositionUpdateInformation [storeId=" + storeId + ", id=" + id + ", title=" + title
        + ", jobType=" + jobType + ", jobDivision=" + jobDivision + ", toString()="
        + super.toString() + "]";
  }


}
