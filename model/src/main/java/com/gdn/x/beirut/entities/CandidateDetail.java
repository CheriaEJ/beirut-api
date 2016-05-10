package com.gdn.x.beirut.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = "CandidateDetail")
public class CandidateDetail extends GdnBaseEntity {

  public static final String CANDIDATE_ID = "candidate_id";

  public static final String CONTENT = "content";

  @OneToOne
  @JoinColumn(name = CandidateDetail.CANDIDATE_ID)
  private Candidate candidate;

  @Column(name = CandidateDetail.CONTENT)
  private byte[] content;

  public CandidateDetail() {
    super();
  }

  public CandidateDetail(String STORE_ID) {
    super();
    this.setStoreId(STORE_ID);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final CandidateDetail other = (CandidateDetail) obj;
    if (candidate == null) {
      if (other.candidate != null)
        return false;
    } else if (!candidate.equals(other.candidate))
      return false;
    if (!Arrays.equals(content, other.content))
      return false;
    return true;
  }

  public Candidate getCandidate() {
    return candidate;
  }


  public byte[] getContent() {
    return this.content;
  }


  public Candidate getIdCandidate() {
    return this.candidate;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((candidate == null) ? 0 : candidate.hashCode());
    result = prime * result + Arrays.hashCode(content);
    return result;
  }


  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }


  public void setContent(byte[] content) {
    this.content = content;
  }


  public void setIdCandidate(Candidate idCandidate) {
    this.candidate = idCandidate;
  }

}
