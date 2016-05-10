package com.gdn.x.beirut.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = "CandidatePosition")
public class CandidatePosition extends GdnBaseEntity {

  @Column(name = "status")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "position_id")
  private Position position;

  @ManyToOne
  @JoinColumn(name = "candidate_id")
  private Candidate candidate;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidatePosition")
  private Set<StatusLog> statusLogs;

  public CandidatePosition() {
    super();
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

}
