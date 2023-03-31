package com.twitter.search.earlybird.exception;

import com.twitter.common.zookeeper.ServerSet;

/**
 * Used when trying to leave a server set when this earlybird is already out of the server set.
 */
public class NotInServerSetUpdateException extends ServerSet.UpdateException {
  public NotInServerSetUpdateException(String message) {
    super(message);
  }
}
