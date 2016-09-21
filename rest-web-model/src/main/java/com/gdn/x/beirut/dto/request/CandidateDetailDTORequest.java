package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDetailDTORequest extends BaseRequest {

  private static final long serialVersionUID = -2167166033564574583L;
  private byte[] content;

  private String mediaType;

  private String filename;

  public byte[] getContent() {
    return content;
  }

  private String getFilename() {
    return filename;
  }

  private String getMediaType() {
    return mediaType;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  private void setFilename(String filename) {
    this.filename = filename;
  }

  private void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }
}
