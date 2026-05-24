package com.twitter.search.earlybird.common.userupdates;

import java.util.Date;

import com.twitter.search.common.indexing.thriftjava.UserUpdateType;

/**
 * Contains an update for a user.
 */
public class UserUpdate {
  public final long twitterUserID;
  public final UserUpdateType updateType;
  public final int updateValue;
  private final Date updatedAt;

  public UserUpdate(long twitterUserID,
                    UserUpdateType updateType,
                    int updateValue,
                    Date updatedAt) {

    this.twitterUserID = twitterUserID;
    this.updateType = updateType;
    this.updateValue = updateValue;
    this.updatedAt = (Date) updatedAt.clone();
  }

  @Override public String toString() {
    return "UserInfoUpdate[userID=" + twitterUserID + ",updateType=" + updateType
           + ",updateValue=" + updateValue + ",updatedAt=" + getUpdatedAt() + "]";
  }

  /**
   * Returns a copy of the updated-at date.
   */
  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }
}
