package com.gdn.x.beirut.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;


@Entity
@Table(name = StatusLog.TABLE_NAME)
public class StatusLog extends GdnBaseEntity {

  private static final long serialVersionUID = 1950426766708644908L;
  public static final String TABLE_NAME = "status_log";
  public static final String COLUMN_STATUS = "status";
  public static final String COLUMN_CANDIDATE_POSITION_ID = "candidate_position_id";

  @Column(name = COLUMN_STATUS)
  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = COLUMN_CANDIDATE_POSITION_ID)
  private CandidatePosition candidatePosition;

  public StatusLog() {
    // nothing to do here
  }

  public StatusLog(CandidatePosition candidatePosition, Status status) {
    this.candidatePosition = candidatePosition;
    this.status = status;
  }

  public CandidatePosition getCandidatePosition() {
    return candidatePosition;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((candidatePosition == null) ? 0 : candidatePosition.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    return result;
  }

  public void setCandidatePosition(CandidatePosition candidatePostition) {
    this.candidatePosition = candidatePostition;
  }

  public void setStatus(Status newStatus) {
    this.status = newStatus;
  }

  // public void setOldStatus(Status oldStatus) {
  // this.oldStatus = oldStatus;
  // }
}
