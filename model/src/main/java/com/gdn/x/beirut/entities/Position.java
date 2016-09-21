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
  public static final String DESCRIPTION = "description";
  public static final String JOB_TYPE = "job_type";
  public static final String JOB_DIVISION = "job_division";
  @Column(name = Position.COLUMN_TITLE)
  private String title;

  @Column(name = Position.DESCRIPTION)
  private String description;
  @Column(name = Position.JOB_TYPE)
  private String jobType;
  @Column(name = Position.JOB_DIVISION)
  private String jobDivision;
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

  public String getDescription() {
    return description;
  }

  public String getJobDivision() {
    return jobDivision;
  }

  public String getJobType() {
    return jobType;
  }

  public String getTitle() {
    return title;
  }

  public void setCandidatePositions(List<CandidatePosition> candidatePositions) {
    this.candidatePositions = candidatePositions;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  // @Override
  // public String toString() {
  // return "Position [title=" + title + ", candidatePositions=" + candidatePositions + "]";
  // }
}
