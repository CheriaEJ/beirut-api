package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {

  private static final long serialVersionUID = -8589947552434752350L;
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String toStringz() {
    return "PositionDTORequest [title=" + title + "]";
  }
}
