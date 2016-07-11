package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class UpdateCandidateStatusModelDTORequest extends BaseRequest {

  private static final long serialVersionUID = -4152825937934296238L;

  private String statusDTORequest;

  private String idPosition;

  private List<String> idCandidates;

  public List<String> getIdCandidates() {
    return idCandidates;
  }

  public String getIdPosition() {
    return idPosition;
  }

  public String getStatusDTORequest() {
    return statusDTORequest;
  }

  public void setIdCandidates(List<String> idCandidates) {
    this.idCandidates = idCandidates;
  }

  public void setIdPosition(String idPosition) {
    this.idPosition = idPosition;
  }

  public void setStatusDTORequest(String statusDTORequest) {
    this.statusDTORequest = statusDTORequest;
  }

}
