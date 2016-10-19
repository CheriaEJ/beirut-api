package com.gdn.x.beirut.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = CandidatePosition.TABLE_NAME)
public class CandidatePosition extends GdnBaseEntity {

  private static final long serialVersionUID = -7151188804914001010L;
  public static final String TABLE_NAME = "candidate_position";
  public static final String COLUMN_STATUS = "status";
  public static final String COLUMN_POSITION_ID = "position_id";
  public static final String COLUMN_CANDIDATE_ID = "candidate_id";

  @Column(name = COLUMN_STATUS)
  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = COLUMN_POSITION_ID)
  private Position position;

  @ManyToOne
  @JoinColumn(name = COLUMN_CANDIDATE_ID)
  private Candidate candidate;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidatePosition")
  private Set<StatusLog> statusLogs = new HashSet<StatusLog>();

  public CandidatePosition() {
    this.status = Status.APPLY;
  }

  public CandidatePosition(Candidate candidate, Position position, String storeId) {
    this.candidate = candidate;
    this.position = position;
    this.setStoreId(storeId);
    this.status = Status.APPLY;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  public Position getPosition() {
    return position;
  }

  public Status getStatus() {
    return status;
  }

  public Set<StatusLog> getStatusLogs() {
    return statusLogs;
  }

  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setStatusLogs(Set<StatusLog> statusLogs) {
    this.statusLogs = statusLogs;
  }

  // public String toStringz() {
  // return "CandidatePosition [status=" + status + ", position=" + position + ", candidate="
  // + candidate + ", statusLogs=" + statusLogs + "]";
  // }

}
