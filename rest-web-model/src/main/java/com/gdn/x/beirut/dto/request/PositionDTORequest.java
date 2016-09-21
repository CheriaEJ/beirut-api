package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {

  private static final long serialVersionUID = -8589947552434752350L;
  private String id;
  private String title;
  private String description;
  private String jobType;
  private String jobDivision;

  private String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }

  private String getJobDivision() {
    return jobDivision;
  }

  private String getJobType() {
    return jobType;
  }

  public String getTitle() {
    return title;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public void setId(String id) {
    this.id = id;
  }

  private void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  private void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String toStringz() {
    return "PositionDTORequest [title=" + title + "]";
  }
}
