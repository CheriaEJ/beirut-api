package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateUpdateStatus extends GdnBaseDomainEventModel {
  private String storeId;

  private String idPosition;

  private String idCandidate;

  private String status;


  public String getIdCandidate() {
    return idCandidate;
  }

  public String getIdPosition() {
    return idPosition;
  }

  public String getStatus() {
    return status;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setIdCandidate(String idCandidate) {
    this.idCandidate = idCandidate;
  }

  public void setIdPosition(String idPosition) {
    this.idPosition = idPosition;
  }


  public void setStatus(String status) {
    this.status = status;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  @Override
  public String toString() {
    return "CandidateUpdateStatus [storeId=" + storeId + ", idPosition=" + idPosition
        + ", idCandidate=" + idCandidate + ", status=" + status + ", toString()=" + super.toString()
        + "]";
  }

}
