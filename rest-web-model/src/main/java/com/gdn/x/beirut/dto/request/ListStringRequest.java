package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class ListStringRequest extends BaseRequest {

  private static final long serialVersionUID = 2202239662877658018L;
  private List<String> values;

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }
}
