package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDetailDTORequest extends BaseRequest {

  private byte[] content;

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
