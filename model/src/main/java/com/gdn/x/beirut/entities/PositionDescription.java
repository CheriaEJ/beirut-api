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
@Table(name = PositionDescription.TABLE_NAME)
public class PositionDescription implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 6328710858098199379L;
  public static final String TABLE_NAME = "position_detail";
  public static final String CONTENT_DESCRIPTION = "content_description";
  public static final String MEDIA_TYPE = "media_type";
  public static final String FILENAME = "filename";

  @Id
  private String id;

  @Lob
  @Column(name = PositionDescription.CONTENT_DESCRIPTION, nullable = false, length = 12000)
  private byte[] contentDescription;

  @Column(name = PositionDescription.MEDIA_TYPE)
  private String mediaType;

  @Column(name = PositionDescription.FILENAME)
  private String filename;

  @MapsId
  @OneToOne
  @JoinColumn(name = GdnBaseEntity.ID)
  private Position position;

  public PositionDescription() {
    // nothing to do here
  }

  public PositionDescription(Position position) {
    this.position = position;
  }

  public byte[] getContentDescription() {
    return contentDescription;
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

  public Position getPosition() {
    return position;
  }

  public void setContentDescription(byte[] contentDescription) {
    this.contentDescription = contentDescription;
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

  public void setPosition(Position position) {
    this.position = position;
  }
}
