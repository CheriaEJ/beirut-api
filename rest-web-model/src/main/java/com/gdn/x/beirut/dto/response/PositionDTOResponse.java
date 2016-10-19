package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class PositionDTOResponse extends BaseResponse {

  private static final long serialVersionUID = -3472124544225110104L;

  private String title;

  private String jobType;

  private String jobDivision;

  public String getJobDivision() {
    return jobDivision;
  }

  public String getJobType() {
    return jobType;
  }

  public String getTitle() {
    return title;
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
}
