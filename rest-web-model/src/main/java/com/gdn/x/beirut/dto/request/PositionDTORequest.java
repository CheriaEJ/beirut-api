package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {

  private static final long serialVersionUID = -8589947552434752350L;
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

  public void setTitle(String title) {
    this.title = title;
  }

  public String toStringz() {
    return "PositionDTORequest [title=" + title + "]";
  }

}
