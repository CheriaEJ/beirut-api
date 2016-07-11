package com.gdn.x.beirut.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class CandidatePositionDTOResponse extends BaseResponse {

  private static final long serialVersionUID = -3273964598673184775L;

  private final StatusDTOResponse status;

  private PositionDTOResponse position;

  private CandidateDTOResponse candidate;

  private final Set<StatusLogDTOResponse> statusLogs = new HashSet<StatusLogDTOResponse>();

  public CandidatePositionDTOResponse() {
    this.status = StatusDTOResponse.APPLY;
  }

  public CandidateDTOResponse getCandidate() {
    return candidate;
  }

  public PositionDTOResponse getPosition() {
    return position;
  }

  public StatusDTOResponse getStatus() {
    return status;
  }

  public Set<StatusLogDTOResponse> getStatusLogs() {
    return statusLogs;
  }

  public void setCandidate(CandidateDTOResponse candidate) {
    this.candidate = candidate;
  }

  public void setPosition(PositionDTOResponse position) {
    this.position = position;
  }

  public String toStringz() {
    return "CandidatePositionDTOResponse [position=" + position + ", candidate=" + candidate
        + ", status=" + status + ", statusLogs=" + statusLogs + "]";
  }
}
