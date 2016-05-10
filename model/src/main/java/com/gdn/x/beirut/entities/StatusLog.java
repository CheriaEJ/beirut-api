
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
@Table(name = "StatusLog")
public class StatusLog extends GdnBaseEntity {

  // @Column(name = "newStatus")
  // private enum newStatus {
  // OPEN, ONHOLD, DECLINED, WITHDRAWL, JOIN
  // }

  // @Column(name = "oldStatus")
  // private Status oldStatus;
  //
  // @Column(name = "newStatus")
  // private Status newStatus;

  @Column(name = "status")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "candidatePosition")
  private CandidatePosition candidatePosition;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final StatusLog other = (StatusLog) obj;
    if (candidatePosition == null) {
      if (other.candidatePosition != null)
        return false;
    } else if (!candidatePosition.equals(other.candidatePosition))
      return false;
    if (status != other.status)
      return false;
    return true;
  }

  public CandidatePosition getCandidatePosition() {
    return candidatePosition;
  }

  @Column(name = "newStatus")
  @Enumerated(EnumType.STRING)
  private Status getStatus() {
    return status;
  }
  //
  // @Column(name = "oldStatus")
  // @Enumerated(EnumType.STRING)
  // private Status getOldStatus() {
  // return oldStatus;
  // }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((candidatePosition == null) ? 0 : candidatePosition.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    return result;
  }

  public void setCandidatePosition(CandidatePosition candidatePostition) {
    // TODO Auto-generated method stub
    this.candidatePosition = candidatePostition;
  }

  public void setStatus(Status newStatus) {
    this.status = newStatus;
  }

  // public void setOldStatus(Status oldStatus) {
  // this.oldStatus = oldStatus;
  // }
}
