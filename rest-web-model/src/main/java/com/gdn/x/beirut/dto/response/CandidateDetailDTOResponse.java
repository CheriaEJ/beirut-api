package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDetailDTOResponse extends BaseResponse {

  private static final long serialVersionUID = 808732336048185462L;
  private byte[] content;

  public CandidateDetailDTOResponse() {
    super();
  }

  public CandidateDetailDTOResponse(String STORE_ID) {
    super();
    this.setStoreId(STORE_ID);
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

}
