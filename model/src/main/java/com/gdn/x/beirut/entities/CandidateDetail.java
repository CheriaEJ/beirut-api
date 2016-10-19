package com.gdn.x.beirut.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = CandidateDetail.TABLE_NAME)
public class CandidateDetail implements Serializable {

  private static final long serialVersionUID = -5841108367030595782L;
  public static final String TABLE_NAME = "candidate_detail";
  public static final String COLUMN_CONTENT = "content";
  public static final String MEDIA_TYPE = "media_type";
  public static final String FILENAME = "filename";

  @Id
  private String id;

  @Lob
  @Column(name = CandidateDetail.COLUMN_CONTENT, nullable = false, length = 12000)
  private byte[] content;

  @Column(name = CandidateDetail.MEDIA_TYPE)
  private String mediaType;

  @Column(name = CandidateDetail.FILENAME)
  private String filename;

  @MapsId
  @OneToOne
  @JoinColumn(name = GdnBaseEntity.ID)
  private Candidate candidate;

  public CandidateDetail() {
    // nothing to do here
  }

  public CandidateDetail(Candidate candidate) {
    this.candidate = candidate;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  public byte[] getContent() {
    return content;
  }

  public String getFilename() {
    return filename;
  }

  public String getId() {
    return id;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }
}
