package com.twitter.search.common.relevance.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The object for quoted message
  */
public class TwitterQuotedMessage {
  private final long quotedStatusId;
  private final long quotedUserId;

  public TwitterQuotedMessage(long quotedStatusId, long quotedUserId) {
    this.quotedStatusId = quotedStatusId;
    this.quotedUserId = quotedUserId;
  }

  public long getQuotedStatusId() {
    return quotedStatusId;
  }

  public long getQuotedUserId() {
    return quotedUserId;
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
