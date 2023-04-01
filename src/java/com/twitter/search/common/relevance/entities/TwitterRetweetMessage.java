package com.twitter.search.common.relevance.entities;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TwitterRetweetMessage {
  // based on original tweet
  private Long sharedId;

  // TwitterMessageUtil checks them
  private String sharedUserDisplayName;
  private Long sharedUserTwitterId = TwitterMessage.LONG_FIELD_NOT_PRESENT;

  private Date sharedDate = null;

  // based on retweet
  private Long retweetId;

  public Long getRetweetId() {
    return retweetId;
  }

  public void setRetweetId(Long retweetId) {
    this.retweetId = retweetId;
  }

  public Long getSharedId() {
    return sharedId;
  }

  public void setSharedId(Long sharedId) {
    this.sharedId = sharedId;
  }

  public String getSharedUserDisplayName() {
    return sharedUserDisplayName;
  }

  public void setSharedUserDisplayName(String sharedUserDisplayName) {
    this.sharedUserDisplayName = sharedUserDisplayName;
  }

  public Long getSharedUserTwitterId() {
    return sharedUserTwitterId;
  }

  public boolean hasSharedUserTwitterId() {
    return sharedUserTwitterId != TwitterMessage.LONG_FIELD_NOT_PRESENT;
  }

  public void setSharedUserTwitterId(Long sharedUserTwitterId) {
    this.sharedUserTwitterId = sharedUserTwitterId;
  }

  public Date getSharedDate() {
    return sharedDate;
  }

  public void setSharedDate(Date sharedDate) {
    this.sharedDate = sharedDate;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
