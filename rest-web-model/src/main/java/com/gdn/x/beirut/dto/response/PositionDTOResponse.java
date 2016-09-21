package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class PositionDTOResponse extends BaseResponse {
  private static final long serialVersionUID = -3472124544225110104L;
  private String title;
  private String description;
  private String jobType;
  private String jobDivision;

  private String getDescription() {
    return description;
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

  private void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  private void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
