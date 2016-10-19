package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateMarkForDelete extends GdnBaseDomainEventModel {

  private String id;

  private String storeId;

  private boolean markForDelete;

  public String getId() {
    return id;
  }

  public String getStoreId() {
    return storeId;
  }

  public boolean isMarkForDelete() {
    return markForDelete;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  @Override
  public String toString() {
    return "CandidateMarkForDelete [id=" + id + ", storeId=" + storeId + ", markForDelete="
        + markForDelete + ", toString()=" + super.toString() + "]";
  }



}
