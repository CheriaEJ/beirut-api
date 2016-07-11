package com.gdn.x.beirut.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = Position.TABLE_NAME)
public class Position extends GdnBaseEntity {

  private static final long serialVersionUID = 628701914919251441L;
  public static final String TABLE_NAME = "position";
  public static final String COLUMN_TITLE = "title";

  @Column(name = Position.COLUMN_TITLE)
  private String title;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "position")
  private List<CandidatePosition> candidatePositions = new ArrayList<CandidatePosition>();

  public Position() {
    // nothing to do here
  }

  public void addCandidatePosition(CandidatePosition candpos) {
    this.candidatePositions.add(candpos);
  }

  public List<CandidatePosition> getCandidatePositions() {
    return candidatePositions;
  }

  public String getTitle() {
    return title;
  }

  public void setCandidatePositions(List<CandidatePosition> candidatePositions) {
    this.candidatePositions = candidatePositions;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  // @Override
  // public String toString() {
  // return "Position [title=" + title + ", candidatePositions=" + candidatePositions + "]";
  // }
}
