package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionMarkForDelete extends GdnBaseDomainEventModel {
  private String id;
  private String storeId;

  public String getId() {
    return id;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  @Override
  public String toString() {
    return "PositionMarkForDelete [id=" + id + ", storeId=" + storeId + ", toString()="
        + super.toString() + "]";
  }
}
