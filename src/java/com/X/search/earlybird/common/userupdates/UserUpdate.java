package com.X.search.earlybird.common.userupdates;

import java.util.Date;

import com.X.search.common.indexing.thriftjava.UserUpdateType;

/**
 * Contains an update for a user.
 */
public class UserUpdate {
  public final long XUserID;
  public final UserUpdateType updateType;
  public final int updateValue;
  private final Date updatedAt;

  public UserUpdate(long XUserID,
                    UserUpdateType updateType,
                    int updateValue,
                    Date updatedAt) {

    this.XUserID = XUserID;
    this.updateType = updateType;
    this.updateValue = updateValue;
    this.updatedAt = (Date) updatedAt.clone();
  }

  @Override public String toString() {
    return "UserInfoUpdate[userID=" + XUserID + ",updateType=" + updateType
           + ",updateValue=" + updateValue + ",updatedAt=" + getUpdatedAt() + "]";
  }

  /**
   * Returns a copy of the updated-at date.
   */
  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }
}
