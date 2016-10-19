package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDetailDTOResponse extends BaseResponse {

  private static final long serialVersionUID = 808732336048185462L;
  private byte[] content;
  private String mediaType;
  private String filename;

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

  private String getFilename() {
    return filename;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  private void setFilename(String filename) {
    this.filename = filename;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

}
