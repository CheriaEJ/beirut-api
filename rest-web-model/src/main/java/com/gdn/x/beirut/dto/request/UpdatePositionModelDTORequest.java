package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class UpdatePositionModelDTORequest extends BaseRequest {

  private static final long serialVersionUID = 7420292554287067130L;

  private String idPositionTarget;

  private String title;

  public String getIdPositionTarget() {
    return idPositionTarget;
  }

  public String getTitle() {
    return title;
  }

  public void setIdPositionTarget(String idPositionTarget) {
    this.idPositionTarget = idPositionTarget;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
